package org.jiegiser.train.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.jiegiser.train.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// 实现拦截器

/**
 * Ordered 是优先级接口，实现该接口的类，会按照优先级进行排序，优先级越小越先执行
 */
@Component
@Slf4j
public class LoginMemberFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求路径
        String path = exchange.getRequest().getURI().getPath();

        // 排除不需要拦截的请求
        if (path.contains("/admin")
                || path.contains("/redis")
                || path.contains("/test")
                || path.contains("/member/member/login")
                || path.contains("/member/member/registry")
                || path.contains("/member/member/send-code")
                || path.contains("/business/kaptcha")) {
            log.info("不需要登录验证：{}", path);
            return chain.filter(exchange);
        } else {
            log.info("需要登录验证：{}", path);
        }
        // 获取 header 的 token 参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("登录验证开始，token：{}", token);
        if (token == null || token.isEmpty()) {
            log.info( "token 为空，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 校验 token 是否有效，包括 token 是否被改过，是否过期
        boolean validate = JwtUtil.validate(token);
        if (validate) {
            log.info("token 有效，放行该请求");
            return chain.filter(exchange);
        } else {
            log.warn( "token 无效，请求被拦截" );
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }

    /**
     * 优先级设置  值越小  优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

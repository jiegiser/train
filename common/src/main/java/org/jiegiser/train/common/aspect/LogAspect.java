package org.jiegiser.train.common.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
public class LogAspect {
    public LogAspect() {
        System.out.println("Common LogAspect - Spring Boot 3.x with Fastjson2");
    }

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    // 定义敏感字段集合（可扩展）暂时去除 "mobile"
    private static final Set<String> SENSITIVE_FIELDS = new HashSet<>(Arrays.asList(
            "password", "pwd", "passwd", "secret", "token",
            "idCard", "idcard", "cardNo", "phone",
            "email", "bankCard", "creditCard", "cvv"
    ));

    // 创建属性过滤器（Fastjson2方式）
    private final PropertyFilter sensitiveFieldFilter = (object, name, value) -> {
        // 排除敏感字段
        if (name == null) return true;
        return !SENSITIVE_FIELDS.contains(name.toLowerCase());
    };

    /**
     * 定义一个切点
     */
    @Pointcut("execution(public * org.jiegiser..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 获取请求属性
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();

            if (attributes == null) {
                LOG.warn("无法获取请求属性");
                return;
            }

            // 增加日志流水号
            MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));

            HttpServletRequest request = attributes.getRequest();
            Signature signature = joinPoint.getSignature();
            String name = signature.getName();

            // 打印请求信息
            LOG.info("------------- 开始 -------------");
            LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
            LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
            LOG.info("远程地址: {}", getClientIp(request));

            // 处理并打印请求参数
            logRequestParameters(joinPoint);

        } catch (Exception e) {
            LOG.error("记录请求日志时发生异常", e);
        }
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;

        try {
            // 执行目标方法
            result = proceedingJoinPoint.proceed();

            // 记录返回结果
            logResponseResult(result);

        } catch (Throwable e) {
            LOG.error("执行方法时发生异常: {}.{}",
                    proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                    proceedingJoinPoint.getSignature().getName(), e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            LOG.info("------------- 结束 耗时：{} ms -------------", endTime - startTime);
        }

        return result;
    }

    /**
     * 记录请求参数（过滤敏感信息和特殊类型）
     */
    private void logRequestParameters(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            LOG.info("请求参数: 无");
            return;
        }

        // 过滤特殊类型的参数
        Object[] filteredArgs = Arrays.stream(args)
                .filter(arg -> !(arg instanceof ServletRequest ||
                        arg instanceof ServletResponse ||
                        arg instanceof MultipartFile))
                .toArray();

        if (filteredArgs.length == 0) {
            LOG.info("请求参数: [已过滤特殊类型]");
            return;
        }

        try {
            // 使用 Fastjson2 序列化，并应用敏感字段过滤器
            String jsonStr = JSON.toJSONString(filteredArgs, sensitiveFieldFilter);
            LOG.info("请求参数: {}", jsonStr);
        } catch (Exception e) {
            LOG.warn("序列化请求参数失败: {}", e.getMessage());
            // 降级处理：使用简单格式
            LOG.info("请求参数: {}", Arrays.toString(filteredArgs));
        }
    }

    /**
     * 记录响应结果（过滤敏感信息）
     */
    private void logResponseResult(Object result) {
        if (result == null) {
            LOG.info("返回结果: null");
            return;
        }

        try {
            // 使用 Fastjson2 序列化，并应用敏感字段过滤器
            String jsonStr = JSON.toJSONString(result, sensitiveFieldFilter);

            // 控制日志长度，避免过长
            if (jsonStr.length() > 1000) {
                LOG.info("返回结果: {}... [长度: {} 字符]",
                        jsonStr.substring(0, 1000), jsonStr.length());
            } else {
                LOG.info("返回结果: {}", jsonStr);
            }
        } catch (Exception e) {
            LOG.warn("序列化返回结果失败: {}", e.getMessage());
            LOG.info("返回结果类型: {}", result.getClass().getSimpleName());
        }
    }

    /**
     * 获取客户端 IP 地址（Spring Boot 3.x 兼容）
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个 IP 时取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }

    /**
     * 替代原 PropertyPreFilters 的方案（兼容旧代码模式）
     */
    private SimplePropertyPreFilter createPropertyPreFilter(String... excludes) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().addAll(Arrays.asList(excludes));
        return filter;
    }
}
package org.jiegiser.train.member.config;

import jakarta.annotation.Resource;
import org.jiegiser.train.common.interceptor.LogInterceptor;
import org.jiegiser.train.common.interceptor.MemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

   @Resource
   MemberInterceptor memberInterceptor;

    @Resource
    LogInterceptor logInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(logInterceptor);

       // 路径不要包含 context-path
       registry.addInterceptor(memberInterceptor)
               .addPathPatterns("/**")
               .excludePathPatterns(
                       "/test",
                       "/member/send-code",
                       "/member/login"
               );
   }
}

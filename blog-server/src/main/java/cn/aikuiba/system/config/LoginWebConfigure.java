package cn.aikuiba.system.config;

import cn.aikuiba.system.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 蛮小满Sama at 2023/11/30 12:06
 *
 * @description
 */
@Configuration
public class LoginWebConfigure implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 配置登录拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/login/**") // 放行登录请求
                .excludePathPatterns("/minio");  //放行minio请求
    }
}

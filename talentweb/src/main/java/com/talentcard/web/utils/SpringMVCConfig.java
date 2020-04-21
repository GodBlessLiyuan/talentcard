package com.talentcard.web.utils;

/**
 * @author: jiangzhaojie
 * @date: Created in 8:57 2020/4/20
 * @version: 1.0.0
 * @description:启用跨域配置
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SuppressWarnings("deprecation")
@SpringBootConfiguration
public class SpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    private FilterConfig filterConfig;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(filterConfig).addPathPatterns("/**");
    }
}

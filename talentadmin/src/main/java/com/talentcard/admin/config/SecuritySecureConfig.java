package com.talentcard.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author: xiahui
 * @date: Created in 2020/4/28 18:38
 * @description: Spring Boot Admin 安全监控
 * @version: 1.0
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String contextPath;

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.contextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setTargetUrlParameter("redirectTo");

        http.authorizeRequests()
                .antMatchers(contextPath + "/assets/**").permitAll()
                .antMatchers(contextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(contextPath + "/login").successHandler(handler)
                .and()
                .logout().logoutUrl(contextPath + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}

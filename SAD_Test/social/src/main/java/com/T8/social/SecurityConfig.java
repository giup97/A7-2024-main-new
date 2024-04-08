package com.T8.social;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.T8.social.Service.OAuthUserGoogleService;

import org.springframework.security.core.Authentication;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private OAuthUserGoogleService oAuthUserGoogleService;

    @Autowired
    private GoogleSuccessHandler googleAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/llogin", "/t23/**").permitAll()
            .anyRequest().authenticated()
            .and().oauth2Login().userInfoEndpoint()
            .userService(oAuthUserGoogleService).and()
            .successHandler(googleAuthenticationSuccessHandler);
            }
}
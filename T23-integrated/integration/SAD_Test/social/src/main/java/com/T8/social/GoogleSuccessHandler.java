package com.T8.social;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
//import com.T8.social.temp.User;
import com.example.db_setup.User;

import com.T8.social.Service.UserService;

@Component
public class GoogleSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                OAuthUserGoogle oauthUser = (OAuthUserGoogle) authentication.getPrincipal();
                String email = oauthUser.getEmail();
                User user = userService.getUserByEmail(email);
                
                if (user == null) {
                    user = userService.createUserFromOAuth(oauthUser);                   
                }

                String token = userService.saveToken(user);
                Cookie jwtTokenCookie = new Cookie("jwt", token);
                jwtTokenCookie.setMaxAge(3600);
                response.addCookie(jwtTokenCookie);

                response.sendRedirect("/loginAuth");

    }

    
}

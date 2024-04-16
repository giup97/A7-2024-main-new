package com.T8.social.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.T8.social.OAuthUserGoogle;
import com.T8.social.Service.OAuthUserGoogleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;


@Controller
public class LoginController {

    @GetMapping("/llogin")
    public ModelAndView showLoginForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return new ModelAndView("login");
    }
    
    @GetMapping("/loginAuth")
    public ModelAndView getLoginInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuthUserGoogle oauthUser = (OAuthUserGoogle) authentication.getPrincipal();
        String name = oauthUser.getName();
        String email = oauthUser.getEmail();
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        //return "Name: " + oauthUser.getName() + "<br>Email: " + oauthUser.getEmail();
        return new ModelAndView("homepage");
    }
    
    @GetMapping("/loginWithGoogle")
    public void loginWithGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @Autowired
    private OAuthUserGoogleService oAuthUserGoogleService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Test";
    }

    @GetMapping("/checkService")
    @ResponseBody
    public String checkService() {
        return (oAuthUserGoogleService != null) ? "Service is defined" : "Service is not defined";
    }
}
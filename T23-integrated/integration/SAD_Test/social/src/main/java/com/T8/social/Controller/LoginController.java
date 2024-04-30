/**
 * Questa classe è responsabile di gestire le richieste relative all'autenticazione e al login degli utenti.
 * Contiene metodi per mostrare il modulo di login, ottenere le informazioni di accesso, effettuare il login con Google,
 * verificare lo stato di autenticazione dell'utente e gestire il logout.
 */

package com.T8.social.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    // Questo metodo recupera le informazioni di login dell'utente, è usata solo come test
    @GetMapping("/loginAuth")
    public ModelAndView getLoginInfo(Model model) {
        
        // Recupera l'oggetto di autenticazione corrente
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Ottiene l'utente corrente come un oggetto OAuthUserGoogle
        OAuthUserGoogle oauthUser = (OAuthUserGoogle) authentication.getPrincipal();

        // Recupera il nome e l'email dell'utente
        String name = oauthUser.getName();
        String email = oauthUser.getEmail();
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return new ModelAndView("homepage");
    }
    // Questo metodo reindirizza l'utente alla pagina di autorizzazione di Google per il login
    @GetMapping("/loginWithGoogle")
    public void loginWithGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
    // Questo è un servizio che gestisce le operazioni relative a Google OAuth2, potrebbe non servire, è solo per testare
    @Autowired
    private OAuthUserGoogleService oAuthUserGoogleService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "User is not authenticated";
        } else {
            return "User is authenticated";
        }
    }

    @GetMapping("/checkService")
    @ResponseBody
    public String checkService() {
        return (oAuthUserGoogleService != null) ? "Service is defined" : "Service is not defined";
    }
    // Questo metodo verifica lo stato di un servizio di autenticazione, usato come test
    @GetMapping("/oauthLogout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication after logout: " + authentication);
        response.sendRedirect("/login");
    }
}
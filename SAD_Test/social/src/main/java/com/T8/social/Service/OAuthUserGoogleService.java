package com.T8.social.Service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.T8.social.OAuthUserGoogle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@Service
public class OAuthUserGoogleService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthUserGoogleService.class);
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        LOGGER.info("loadUser method in OAuthUserGoogleService is called");
        return new OAuthUserGoogle(oauth2User);
    }
}

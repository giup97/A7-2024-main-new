package com.T8.social.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.T8.social.OAuthUserGoogle;

import com.example.db_setup.User;
import com.example.db_setup.UserRepository;
import com.example.db_setup.Authentication.AuthenticatedUser;
import com.example.db_setup.Authentication.AuthenticatedUserRepository;

//import com.T8.social.temp.Authentication.AuthenticatedUser;
//import com.T8.social.temp.Authentication.AuthenticatedUserRepository;
//import com.T8.social.temp.User;
//import com.T8.social.temp.UserRepository;   

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;





@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUserFromOAuth(OAuthUserGoogle oauthUser) {
        User newUser = new User();
        newUser.setEmail(oauthUser.getEmail());
        newUser.setName(oauthUser.getName());
        String[] nameParts = oauthUser.getName().split(" ");
        if (nameParts.length > 1) {
            newUser.setSurname(nameParts[nameParts.length - 1]);
        }
        // Set other user properties as needed
        
        return userRepository.save(newUser);
    }
    

    public String saveToken(User user) {

        String token = generateToken(user);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user, token);

        authenticatedUserRepository.save(authenticatedUser);

        return token;
    }

    public static String generateToken(User user) {
    Instant now = Instant.now();
    Instant expiration = now.plus(1, ChronoUnit.HOURS);

    String token = Jwts.builder()
            .setSubject(user.getEmail())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiration))
            .claim("userId", user.getID())
            .claim("role", "user")
            .signWith(SignatureAlgorithm.HS256, "mySecretKey")
            .compact();

    return token;
}

}

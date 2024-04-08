package com.T8.social.temp.Authentication;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.T8.social.temp.User;

import lombok.Data;

@Entity
@Table(name = "authenticated_users")
@Data
public class AuthenticatedUser {
    
    @Id
    private String authToken;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AuthenticatedUser(com.T8.social.temp.User user2, String authToken) {
        this.user = user2;
        this.authToken = authToken;
    }

    public AuthenticatedUser() {
    }
}
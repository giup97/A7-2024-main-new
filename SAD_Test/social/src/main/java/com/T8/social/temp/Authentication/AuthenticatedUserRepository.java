package com.T8.social.temp.Authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, String> {

    AuthenticatedUser findByAuthToken(String authToken);
}
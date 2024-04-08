package com.T8.social.temp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    User findByEmail(String email);
    User findByName(String name);
    User findByResetToken(String resetToken);
    List<User> findAll();

}
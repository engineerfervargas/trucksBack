package com.test.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
    public Optional<User> findByEmail(String email);
    
}

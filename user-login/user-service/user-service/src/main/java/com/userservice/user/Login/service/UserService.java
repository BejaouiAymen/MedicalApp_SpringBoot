package com.userservice.user.Login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import com.userservice.user.Login.exception.UserNotFoundException;
import com.userservice.user.Login.repo.UserRepo;
import com.userservice.user.Login.model.User;


@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

   
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User by email " + email + " was not found"));
    }
  
}
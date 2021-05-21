package com.userservice.user.Login.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.userservice.user.Login.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

}

package com.pfa.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.pfa.user.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteUserById(Long id);

    Optional<User> findUserById(Long id);
}

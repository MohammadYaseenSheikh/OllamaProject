package com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject;

import com.yaseen.code.deepseekproject.com.yaseen.code.deepseekproject.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}

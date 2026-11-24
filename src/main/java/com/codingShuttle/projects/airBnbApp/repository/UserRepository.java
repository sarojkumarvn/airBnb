package com.codingShuttle.projects.airBnbApp.repository;

import com.codingShuttle.projects.airBnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByEmail(String email);
}

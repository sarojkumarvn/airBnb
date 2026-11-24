package com.codingShuttle.projects.airBnbApp.repository;

import com.codingShuttle.projects.airBnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}

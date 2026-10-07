package com.codingShuttle.projects.airBnbApp.repository;

import com.codingShuttle.projects.airBnbApp.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}

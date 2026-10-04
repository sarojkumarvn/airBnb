package com.codingShuttle.projects.airBnbApp.repository;

import com.codingShuttle.projects.airBnbApp.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Guest , Long> {
}

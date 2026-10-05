package com.codingShuttle.projects.airBnbApp.repository;

import com.codingShuttle.projects.airBnbApp.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

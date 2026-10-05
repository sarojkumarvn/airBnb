package com.codingShuttle.projects.airBnbApp.dto;


import com.codingShuttle.projects.airBnbApp.entity.*;
import com.codingShuttle.projects.airBnbApp.entity.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Payment payment;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;


}

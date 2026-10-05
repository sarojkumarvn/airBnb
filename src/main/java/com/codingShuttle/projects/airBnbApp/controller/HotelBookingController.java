package com.codingShuttle.projects.airBnbApp.controller;


import com.codingShuttle.projects.airBnbApp.dto.BookingDto;
import com.codingShuttle.projects.airBnbApp.dto.BookingRequestDto;
import com.codingShuttle.projects.airBnbApp.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService ;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequestDto)) ;
    }






}

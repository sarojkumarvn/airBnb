package com.codingShuttle.projects.airBnbApp.service;

import com.codingShuttle.projects.airBnbApp.dto.BookingDto;
import com.codingShuttle.projects.airBnbApp.dto.BookingRequestDto;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequestDto bookingRequestDto) ;

}

package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.dto.BookingDto;
import com.codingShuttle.projects.airBnbApp.dto.BookingRequestDto;
import com.codingShuttle.projects.airBnbApp.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository ;

    @Override
    public BookingDto initialiseBooking(BookingRequestDto bookingRequestDto) {
        return null;
    }
}

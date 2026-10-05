package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.dto.BookingDto;
import com.codingShuttle.projects.airBnbApp.dto.BookingRequestDto;
import com.codingShuttle.projects.airBnbApp.entity.*;
import com.codingShuttle.projects.airBnbApp.entity.enums.BookingStatus;
import com.codingShuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingShuttle.projects.airBnbApp.repository.BookingRepository;
import com.codingShuttle.projects.airBnbApp.repository.HotelRepository;
import com.codingShuttle.projects.airBnbApp.repository.InventoryRepository;
import com.codingShuttle.projects.airBnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository ;
    private final HotelRepository hotelRepository ;
    private final RoomRepository roomRepository ;
    private final InventoryRepository inventoryRepository ;
    private final ModelMapper modelMapper ;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequestDto bookingRequestDto) {


        // Add a log here
        log.info("Starting the booking") ;

        Hotel hotel = hotelRepository.findById(bookingRequestDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + bookingRequestDto.getHotelId())) ;


        Room room = roomRepository.findById(bookingRequestDto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + bookingRequestDto.getRoomId())) ;

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId()  ,
                bookingRequestDto.getCheckInDate() ,
                bookingRequestDto.getCheckOutDate() ,
                bookingRequestDto.getRoomsCount()) ;

        long daysCount = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate() , bookingRequestDto.getCheckOutDate()) ;
        if(inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is not available anymore") ;
        }

        // reserve the rooms / update the booked count


        for(Inventory inventory : inventoryList) {
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequestDto.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList) ;

        // create the booking
        User user = new User() ;
        user.setId(1L);

        // TODO calculate dynamic pricing

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build() ;

        booking = bookingRepository.save(booking) ;

        return modelMapper.map(booking , BookingDto.class) ;

    }
}

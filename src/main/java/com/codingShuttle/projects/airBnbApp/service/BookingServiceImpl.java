package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.dto.BookingDto;
import com.codingShuttle.projects.airBnbApp.dto.BookingRequestDto;
import com.codingShuttle.projects.airBnbApp.dto.GuestDto;
import com.codingShuttle.projects.airBnbApp.entity.*;
import com.codingShuttle.projects.airBnbApp.entity.enums.BookingStatus;
import com.codingShuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingShuttle.projects.airBnbApp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final GuestRepository guestRepository ;

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

        // reserve the rooms / update the booked count`


        for(Inventory inventory : inventoryList) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequestDto.getRoomsCount());
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
                .user(getCurrentUser())
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build() ;

        booking = bookingRepository.save(booking) ;

        return modelMapper.map(booking , BookingDto.class) ;

    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {

        // add a log
        log.info("Started adding new guests ") ;


        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found with this id {}" + bookingId)) ;

        if(hasBookingExpired(booking)) {
            throw new IllegalStateException("Booking has already expired") ;
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not under reserved state , so you can not add guests") ;
        }



        // Here added the guests one by one by using loops

        for(GuestDto guestDto : guestDtoList) {
            Guest guest = modelMapper.map(guestDto , Guest.class) ;
            guest.setUser(getCurrentUser()) ;
            guest = guestRepository.save(guest) ;
            booking.getGuests().add(guest) ;

        }
        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking) ;
        return modelMapper.map(booking , BookingDto.class) ;
    }


    public boolean hasBookingExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now()) ;
    }

    public User getCurrentUser() {
        User user = new User() ;
        user.setId(1L);
        return user ;

    }
}

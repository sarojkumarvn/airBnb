package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.dto.HotelDto;
import com.codingShuttle.projects.airBnbApp.entity.Hotel;
import com.codingShuttle.projects.airBnbApp.entity.Room;
import com.codingShuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingShuttle.projects.airBnbApp.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // create a constructor for me with required params
@Slf4j
public class HotelServiceImpl implements HotelService{

    public  final HotelRepository hotelRepository ; // dependency injection
    private final ModelMapper modelMapper ;
    private final InventoryService inventoryService ;



    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with required informations " , hotelDto.getName());
        // convert this to hotel entity
        Hotel hotel = modelMapper.map(hotelDto , Hotel.class) ;
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel) ;
        log.info("created new hotel with id {}" , hotelDto.getId()) ;
        return modelMapper.map(hotel , HotelDto.class) ;
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Finding hotel by idv :" , id) ;
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Hotel Not found with id : {}" + id)) ;
        return modelMapper.map(hotel , HotelDto.class) ;
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));

        hotelRepository.deleteById(id);
        for(Room room: hotel.getRooms()) {
            inventoryService.deleteFutureInventories(room);
        }
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        hotel.setActive(true);

        // assuming only do it once
        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }



}

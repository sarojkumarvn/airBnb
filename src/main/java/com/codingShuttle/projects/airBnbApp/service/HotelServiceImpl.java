package com.codingShuttle.projects.airBnbApp.service;


import com.codingShuttle.projects.airBnbApp.dto.HotelDto;
import com.codingShuttle.projects.airBnbApp.entity.Hotel;
import com.codingShuttle.projects.airBnbApp.exception.ResourceNotFoundException;
import com.codingShuttle.projects.airBnbApp.repository.HotelRepository;
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
    public void deleteHotelById(Long id) {
        log.info("Deleting the hotel with id : {}" + id);
        hotelRepository.deleteById(id);
    }


}

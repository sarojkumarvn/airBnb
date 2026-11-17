package com.codingShuttle.projects.airBnbApp.dto;


import com.codingShuttle.projects.airBnbApp.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HotelPriceDto {
    private Hotel hotel  ;
    private Double price ;
}

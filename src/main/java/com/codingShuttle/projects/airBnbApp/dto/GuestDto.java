package com.codingShuttle.projects.airBnbApp.dto;

import com.codingShuttle.projects.airBnbApp.entity.User;
import com.codingShuttle.projects.airBnbApp.entity.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class GuestDto {




    private Long id;

    private User user;


    private String name;


    private Gender gender;

    private Integer age;
}

package com.codingShuttle.projects.airBnbApp.dto;


import lombok.Data;

@Data
public class SignUpRequestDto {
    private String email , password  ;
    private String name  ;
}

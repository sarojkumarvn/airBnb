package com.codingShuttle.projects.airBnbApp.entity;


import com.codingShuttle.projects.airBnbApp.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;


    @Column(unique = true , nullable = false)
    private String transactionId ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus ;



}

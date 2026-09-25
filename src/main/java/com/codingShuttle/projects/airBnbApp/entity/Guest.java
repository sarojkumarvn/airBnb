package com.codingShuttle.projects.airBnbApp.entity;


import com.codingShuttle.projects.airBnbApp.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user  ;

    @Column(nullable = false)
    private String name  ;

    @Column(nullable = false)
    private Gender gender ;


    @Column(nullable = false)
    private Integer age ;

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    private LocalDateTime updatedAt ;

//    @ManyToMany(mappedBy = "guest" )
//    private Set<Booking> bookings ;



}

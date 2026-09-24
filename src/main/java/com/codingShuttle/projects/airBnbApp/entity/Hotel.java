package com.codingShuttle.projects.airBnbApp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String name  ;

    private String city ;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos ;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities ;


    @UpdateTimestamp
    private LocalDateTime createdAt ;

    @UpdateTimestamp

    private LocalDateTime updatedAt ;

    @Embedded
    private HotelContactInfo contactInfo ;



    @Column(nullable = false)
    private Boolean active ;

    @OneToMany(mappedBy = "hotel" , fetch = FetchType.LAZY)
    private List<Room> rooms ; // One hotel have many rooms so one to many [ One is the file --> Many written ]

}


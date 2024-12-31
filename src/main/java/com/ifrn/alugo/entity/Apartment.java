package com.ifrn.alugo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private Integer floor;
    private Boolean available;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "number_of_bathrooms")
    private Integer numberOfBathrooms;

    @Column(name = "area_in_m2")
    private Double areaInM2;
    private Double price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}

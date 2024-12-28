package com.ifrn.alugo.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_price")
    private Double rentalPrice;

    private String description;

    private Boolean available;

    @Column(name = "number_of_bathrooms")
    private Integer numberOfBathrooms;

    @Column(name = "number_of_bedrooms")
    private Integer numberOfBedrooms;

    @Column(name = "area_in_m2")
    private Double areaInM2;

    @Column(name = "has_garage")
    private Boolean hasGarage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
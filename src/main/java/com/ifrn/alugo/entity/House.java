package com.ifrn.domusmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rentalPrice;
    private String description;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Double areaInM2;
    private Boolean hasGarage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
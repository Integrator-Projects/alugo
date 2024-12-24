package com.ifrn.domusmanager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String state;
    private Long zipCode;
    private String street;
    private String neighborhood;
    private Integer number;

    @OneToOne(mappedBy = "address")
    private House house;

    @OneToOne(mappedBy = "address")
    private Building building;
}

package com.ifrn.alugo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_price")
    @PositiveOrZero(message = "Valor do aluguel deve ser positivo")
    private Double rentalPrice;

    private String description;

    private Boolean available = true;

    @Column(name = "number_of_bathrooms")

    @Positive(message = "Número de banheiros deve ser maior que zero")
    private Integer numberOfBathrooms;

    @Column(name = "number_of_bedrooms")
    @Positive(message = "Número de quartos deve ser maior que zero")
    private Integer numberOfBedrooms;

    @Column(name = "area_in_m2")
    @Positive(message = "Área deve ser maior que zero")
    private Double areaInM2;

    @Column(name = "has_garage")
    private Boolean hasGarage = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
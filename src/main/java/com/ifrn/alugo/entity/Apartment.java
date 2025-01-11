package com.ifrn.alugo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Número não pode ser nulo")
    @Positive(message = "Número deve ser maior que zero")
    private Integer number;

    @NotNull(message = "Andar não pode ser nulo")
    @PositiveOrZero(message = "Andar não pode ser negativo")
    private Integer floor;

    private Boolean available = true;

    @Column(name = "number_of_rooms")
    @PositiveOrZero(message = "Número de quartos não pode ser negativo")
    private Integer numberOfRooms;

    @Column(name = "number_of_bathrooms")
    @Positive(message = "Número de banheiros deve ser maior que zero")
    private Integer numberOfBathrooms;

    @Column(name = "area_in_m2")
    @Positive(message = "Área deve ser maior que zero")
    private Double areaInM2;

    @Positive(message = "Preço do aluguel não pode ser negativo")
    private Double price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}

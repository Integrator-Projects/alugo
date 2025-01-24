package com.ifrn.alugo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated("mapstruct")
public class HouseResponseDTO {
    private Long id;
    private Double rentalPrice;
    private String description;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Double areaInM2;
    private Boolean hasGarage;

    private AddressResponseDTO address;
}

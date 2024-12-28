package com.ifrn.alugo.dto;

import lombok.Data;

@Data
public class HouseRequestDTO {
    private Double rentalPrice;
    private String description;
    private Boolean available;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Double areaInM2;
    private Boolean hasGarage;

    private AddressRequestDTO address;
}

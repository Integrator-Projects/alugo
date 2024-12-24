package com.ifrn.domusmanager.dto;

import lombok.Data;

@Data
public class HouseRequestDTO {
    private Double rentalPrice;
    private String description;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private Double areaInM2;
    private Boolean hasGarage;

    private AddressRequestDTO address;
}

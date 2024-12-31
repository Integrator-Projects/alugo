package com.ifrn.alugo.dto;

import lombok.Data;

@Data
public class BuildingRequestDTO {
    private Integer numberOfFloors;
    private String description;
    private AddressRequestDTO address;
}

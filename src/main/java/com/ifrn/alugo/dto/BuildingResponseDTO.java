package com.ifrn.alugo.dto;

import lombok.Data;

@Data
public class BuildingResponseDTO {
    private Long id;
    private Integer numberOfFloors;
    private String description;

    private AddressResponseDTO address;
}

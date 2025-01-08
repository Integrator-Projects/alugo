package com.ifrn.alugo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponseDTO {
    private Long id;
    private Integer numberOfFloors;
    private String description;

    private AddressResponseDTO address;
}

package com.ifrn.alugo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentResponseDTO {
    private Long id;
    private Integer number;
    private Integer floor;
    private Boolean available;
    private Integer numberOfRooms;
    private Integer numberOfBathrooms;
    private Double areaInM2;
    private Double price;
    private String description;

    private BuildingResponseDTO building;
}

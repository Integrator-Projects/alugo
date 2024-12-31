package com.ifrn.alugo.dto;

import com.ifrn.alugo.entity.Building;
import lombok.Data;

@Data
public class ApartmentRequestDTO {
    private Integer number;
    private Integer floor;
    private Boolean available;
    private Integer numberOfRooms;
    private Integer numberOfBathrooms;
    private Double areaInM2;
    private Double price;
    private String description;
    private Long buildingId;
}

package com.ifrn.alugo.dto;

import com.ifrn.alugo.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated("mapstruct")
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

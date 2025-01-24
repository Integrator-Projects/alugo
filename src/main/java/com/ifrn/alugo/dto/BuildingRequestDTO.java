package com.ifrn.alugo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated("mapstruct")
public class BuildingRequestDTO {
    private Integer numberOfFloors;
    private String description;
    private AddressRequestDTO address;
}

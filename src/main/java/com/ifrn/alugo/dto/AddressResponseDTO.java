package com.ifrn.alugo.dto;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String city;
    private String state;
    private String zipCode;
    private String street;
    private String neighborhood;
    private Integer number;
}

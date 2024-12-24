package com.ifrn.domusmanager.dto;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String city;
    private String state;
    private Long zipCode;
    private String street;
    private String neighborhood;
    private Integer number;
}

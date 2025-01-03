package com.ifrn.alugo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private String city;
    private String state;
    private String zipCode;
    private String street;
    private String neighborhood;
    private Integer number;
}

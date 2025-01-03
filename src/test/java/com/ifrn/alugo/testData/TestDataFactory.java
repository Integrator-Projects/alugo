package com.ifrn.alugo.testData;

import com.ifrn.alugo.dto.AddressRequestDTO;
import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.House;

public class TestDataFactory {

    public static final String CITY = "City";
    public static final String STATE = "State";
    public static final String ZIP_CODE = "12345";
    public static final String STREET = "Street";
    public static final String NEIGHBORHOOD = "Neighborhood";
    public static final int NUMBER = 123;

    public static AddressRequestDTO createAddressRequestDTO() {
        return new AddressRequestDTO(CITY, STATE, ZIP_CODE, STREET, NEIGHBORHOOD, NUMBER);
    }

    public static Address createAddressEntity() {
        return new Address(1L, CITY, STATE, ZIP_CODE, STREET, NEIGHBORHOOD, NUMBER);
    }

    public static HouseRequestDTO createHouseRequestDTO(AddressRequestDTO addressRequestDTO) {
        HouseRequestDTO houseRequestDTO = new HouseRequestDTO();
        houseRequestDTO.setAddress(addressRequestDTO);
        return houseRequestDTO;
    }

    public static House createHouseEntity() {
        return new House();
    }
}


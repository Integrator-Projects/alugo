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
    public static final double RENTAL_PRICE = 1500.0;
    public static final String DESCRIPTION = "Beautiful house";
    public static final boolean AVAILABLE = true;
    public static final int NUMBER_OF_BATHROOMS = 2;
    public static final int NUMBER_OF_BEDROOMS = 3;
    public static final double AREA_IN_M2 = 120.0;
    public static final boolean HAS_GARAGE = true;

    public static AddressRequestDTO createAddressRequestDTO() {
        return new AddressRequestDTO(CITY, STATE, ZIP_CODE, STREET, NEIGHBORHOOD, NUMBER);
    }

    public static Address createAddressEntity() {
        return new Address(1L, CITY, STATE, ZIP_CODE, STREET, NEIGHBORHOOD, NUMBER);
    }

    public static Address createAddress(Long id, String city, String state, String zipCode,
                                        String street, String neighborhood, Integer number) {
        return new Address(id, city, state, zipCode, street, neighborhood, number);
    }

    public static HouseRequestDTO createHouseRequestDTO(AddressRequestDTO addressRequestDTO) {
        HouseRequestDTO houseRequestDTO = new HouseRequestDTO();
        houseRequestDTO.setAddress(addressRequestDTO);
        return houseRequestDTO;
    }

    public static House createHouseEntity() {
        return createHouse(1L, RENTAL_PRICE, DESCRIPTION, AVAILABLE, NUMBER_OF_BATHROOMS,
                NUMBER_OF_BEDROOMS, AREA_IN_M2, HAS_GARAGE, createAddressEntity());
    }

    public static House createHouse(Long id, Double rentalPrice, String description, Boolean available,
                                    Integer numberOfBathrooms, Integer numberOfBedrooms,
                                    Double areaInM2, Boolean hasGarage, Address address) {
        return new House(id, rentalPrice, description, available, numberOfBathrooms,
                numberOfBedrooms, areaInM2, hasGarage, address);
    }
}


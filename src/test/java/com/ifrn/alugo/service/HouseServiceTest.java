package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.AddressRequestDTO;
import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.House;
import com.ifrn.alugo.mappers.AddressMapper;
import com.ifrn.alugo.mappers.HouseMapper;
import com.ifrn.alugo.repository.AddressRepository;
import com.ifrn.alugo.repository.HouseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class HouseServiceTest {
    @Mock
    private HouseRepository houseRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private HouseMapper houseMapper;

    @Mock
    private AddressMapper addressMapper;

    private AutoCloseable closeable;

    @InjectMocks
    private HouseService houseService;

    @BeforeEach
    void setUp() {
       closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void destroy() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Não permite criar uma casa com endereço existente")
    public void testCreateHouse_WithExistingAddress_ShouldThrowIllegalArgumentException() {
        HouseRequestDTO houseRequestDTO = new HouseRequestDTO();
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO("City", "State", "12345", "Street", "Neighborhood", 123);
        houseRequestDTO.setAddress(addressRequestDTO);

        Address address = new Address(1L, "City", "State", "12345", "Street", "Neighborhood", 123);
        House existingHouse = new House();
        existingHouse.setAddress(address);

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(houseRepository.findByAddress(address)).thenReturn(Optional.of(existingHouse));

        assertThrows(IllegalArgumentException.class, () -> {
            houseService.createHouse(houseRequestDTO);
        });

        when(houseMapper.toEntity(any(HouseRequestDTO.class))).thenReturn(new House());
        verify(houseRepository, never()).save(any(House.class));
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    @DisplayName("Cria uma casa com endereço novo")
    public void testCreateHouse_WithNonExistingAddress_ShouldCreateHouseSuccessfully(){
        HouseRequestDTO houseRequestDTO = new HouseRequestDTO();
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO("City", "State", "12345", "Street", "Neighborhood", 123);
        houseRequestDTO.setAddress(addressRequestDTO);

        Address address = new Address(1L,"City", "State", "12345", "Street", "Neighborhood", 123);
        House house = new House();

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(houseRepository.findByAddress(address)).thenReturn(Optional.empty());
        when(houseMapper.toEntity(any(HouseRequestDTO.class))).thenReturn(house);
        when(houseMapper.toResponseDTO(any(House.class))).thenReturn(new HouseResponseDTO());

        HouseResponseDTO response = houseService.createHouse(houseRequestDTO);

        assertNotNull(response);
        verify(houseRepository).save(any(House.class));
    }
}
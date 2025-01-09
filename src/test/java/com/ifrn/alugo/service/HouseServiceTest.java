package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.AddressRequestDTO;
import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.House;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.AddressMapper;
import com.ifrn.alugo.mappers.HouseMapper;
import com.ifrn.alugo.repository.AddressRepository;
import com.ifrn.alugo.repository.HouseRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        AddressRequestDTO addressRequestDTO = Instancio.create(AddressRequestDTO.class);
        Address address = Instancio.create(Address.class);
        HouseRequestDTO houseRequestDTO = Instancio.create(HouseRequestDTO.class);
        House existingHouse = Instancio.create(House.class);
        existingHouse.setAddress(address);

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        )).thenReturn(Optional.of(existingHouse));

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
        AddressRequestDTO addressRequestDTO = Instancio.create(AddressRequestDTO.class);
        HouseRequestDTO houseRequestDTO = Instancio.create(HouseRequestDTO.class);
        House house = Instancio.create(House.class);
        Address address = Instancio.create(Address.class);

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        )).thenReturn(Optional.empty());
        when(houseMapper.toEntity(any(HouseRequestDTO.class))).thenReturn(house);
        when(houseMapper.toResponseDTO(any(House.class))).thenReturn(new HouseResponseDTO());

        HouseResponseDTO response = houseService.createHouse(houseRequestDTO);

        assertNotNull(response);
        verify(houseRepository).save(any(House.class));
    }

    @Test
    @DisplayName("Listar todas as casas com sucesso")
    public void listAllHouses_ShouldReturnListOfHouses() {
        House house1 = Instancio.create(House.class);
        House house2 = Instancio.create(House.class);
        List<House> houses = List.of(house1, house2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<House> page = new PageImpl<>(houses, pageable, houses.size());

        when(houseRepository.findAll(pageable)).thenReturn(page);
        when(houseMapper.toResponseDTO(house1)).thenReturn(new HouseResponseDTO());
        when(houseMapper.toResponseDTO(house2)).thenReturn(new HouseResponseDTO());

        Page<HouseResponseDTO> response = houseService.getAllHouses(pageable);

        assertEquals(2, response.getContent().size());
        verify(houseRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Buscar uma casa com ID válido")
    public void findHouseById_WithValidId_ShouldReturnHouse() {
        House house1 = Instancio.create(House.class);
        HouseResponseDTO houseResponseDTO = Instancio.create(HouseResponseDTO.class);
        Long validId = 1L;

        when(houseRepository.findById(validId)).thenReturn(Optional.of(house1));
        when(houseMapper.toResponseDTO(house1)).thenReturn(houseResponseDTO);

        HouseResponseDTO result = houseService.findHouseById(validId);

        assertEquals(houseResponseDTO, result);
        verify(houseRepository).findById(validId);
    }

    @Test
    @DisplayName("Buscar uma casa com ID inválido")
    public void findHouseById_WithInvalidId_ShouldThrowException() {
        Long invalidId = 9999L;
        when(houseRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> houseService.findHouseById(invalidId));

        verify(houseRepository).findById(invalidId);
    }

    @Test
    @DisplayName("Tentar atualizar casa com endereço já cadastrado")
    public void updateHouse_WithExistingAddress_ShouldThrowException() {
        Long houseId = 1L;
        Address existingAddress = Instancio.create(Address.class);
        House existingHouse = Instancio.create(House.class);

        AddressRequestDTO addressRequestDTO = Instancio.create(AddressRequestDTO.class);
        HouseRequestDTO houseRequestDTO = Instancio.create(HouseRequestDTO.class);

        when(houseRepository.findById(houseId)).thenReturn(Optional.of(existingHouse));
        when(addressMapper.toEntity(houseRequestDTO.getAddress())).thenReturn(existingAddress);
        when(houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                existingAddress.getStreet(), existingAddress.getCity(), existingAddress.getZipCode()
        )).thenReturn(Optional.of(existingHouse));

        assertThrows(IllegalArgumentException.class,
                () -> houseService.updateHouse(houseId, houseRequestDTO));

        verify(houseRepository, never()).saveAndFlush(any(House.class));
    }

    @Test
    @DisplayName("Atualizar casa com todos os campos válidos")
    void updateHouse_WithValidFields_ShouldUpdateHouseSuccessfully() {
        Long houseId = 1L;
        HouseRequestDTO houseRequestDTO = Instancio.create(HouseRequestDTO.class);
        Address address = Instancio.create(Address.class);

        House house = Instancio.create(House.class);
        house.setId(houseId);
        house.setAddress(address);

        when(houseRepository.findById(houseId)).thenReturn(Optional.of(house));
        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                house.getAddress().getStreet(), house.getAddress().getCity(), house.getAddress().getZipCode()
        )).thenReturn(Optional.empty());
        when(houseMapper.updateEntityFromRequest(houseRequestDTO, house)).thenReturn(house);
        when(houseRepository.saveAndFlush(house)).thenReturn(house);
        when(houseMapper.toResponseDTO(house)).thenReturn(new HouseResponseDTO());

        HouseResponseDTO houseResponseDTO = houseService.updateHouse(houseId, houseRequestDTO);

        assertNotNull(houseResponseDTO);
        verify(houseRepository).saveAndFlush(house);
    }

    @Test
    @DisplayName("Tentar atualizar casa com id que não existe")
    public void updateHouse_WithInvalidId_ShouldThrowException() {
        Long houseId = 9999L;
        AddressRequestDTO addressRequestDTO = Instancio.create(AddressRequestDTO.class);
        HouseRequestDTO houseRequestDTO = Instancio.create(HouseRequestDTO.class);

        House house = Instancio.create(House.class);
        house.setAddress(Instancio.create(Address.class));

        when(houseRepository.findById(houseId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> houseService.updateHouse(houseId, houseRequestDTO)
        );

        verify(houseRepository).findById(houseId);
        verify(houseRepository, never()).save(any(House.class));
    }

    @Test
    @DisplayName("Excluir casa com ID válido")
    public void deleteHouseById_WithValidId_ShouldDeleteHouseSuccessfully() {
        Long houseId = 1L;

        when(houseRepository.findById(houseId)).thenReturn(
                Optional.of(Instancio.create(House.class))
        );
        when(houseRepository.existsById(houseId)).thenReturn(true);

        houseService.deleteHouse(houseId);

        verify(houseRepository).deleteById(houseId);
    }

    @Test
    @DisplayName("Tentar excluir casa com ID inválido")
    public void deleteHouseById_WithValidId_ShouldThrowException() {
        Long houseId = 9999L;

        when(houseRepository.existsById(houseId)).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> houseService.deleteHouse(houseId)
        );

        verify(houseRepository, never()).deleteById(houseId);
    }

}
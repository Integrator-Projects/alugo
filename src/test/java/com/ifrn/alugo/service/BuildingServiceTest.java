package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.*;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.Building;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.AddressMapper;
import com.ifrn.alugo.mappers.BuildingMapper;
import com.ifrn.alugo.repository.BuildingRepository;
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

class BuildingServiceTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private BuildingMapper buildingMapper;

    @Mock
    private AddressMapper addressMapper;

    private AutoCloseable closeable;

    @InjectMocks
    private BuildingService buildingService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void destroy() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Criar prédio com sucesso")
    public void testCreateBuilding_ShouldCreateSuccessfully() {
        BuildingRequestDTO buildingRequestDTO = Instancio.create(BuildingRequestDTO.class);
        Building building = Instancio.create(Building.class);
        BuildingResponseDTO buildingResponseDTO = Instancio.create(BuildingResponseDTO.class);
        Address address = Instancio.create(Address.class);

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(buildingMapper.toEntity(buildingRequestDTO)).thenReturn(building);
        when(buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        )).thenReturn(Optional.empty());
        when(buildingRepository.save(building)).thenReturn(building);
        when(buildingMapper.toResponseDTO(building)).thenReturn(buildingResponseDTO);

        BuildingResponseDTO result = buildingService.createBuilding(buildingRequestDTO);

        assertEquals(buildingResponseDTO, result);
        verify(buildingRepository).save(building);
    }

    @Test
    @DisplayName("Não permite criar prédio com endereço existente")
    public void testCreateBuilding_ExistingAddress_ShouldThrowException() {
        BuildingRequestDTO buildingRequestDTO = Instancio.create(BuildingRequestDTO.class);
        Building building = Instancio.create(Building.class);
        Building existingBuilding = Instancio.create(Building.class);
        Address address = Instancio.create(Address.class);

        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        )).thenReturn(Optional.of(existingBuilding));
        when(buildingRepository.save(building)).thenReturn(building);

        assertThrows(
                IllegalArgumentException.class,
                () -> buildingService.createBuilding(buildingRequestDTO)
        );

        verify(buildingRepository, never()).save(building);
    }

    @Test
    @DisplayName("Listar tods os prédios com sucesso")
    public void listAllBuildings_ShouldReturnListOfBuildings() {
        Building building1 = Instancio.create(Building.class);
        List<Building> buildings = List.of(building1);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Building> page = new PageImpl<>(buildings, pageable, buildings.size());

        when(buildingRepository.findAll(pageable)).thenReturn(page);
        when(buildingMapper.toResponseDTO(building1)).thenReturn(new BuildingResponseDTO());

        Page<BuildingResponseDTO> response = buildingService.getAllBuildings(pageable);

        assertEquals(1, response.getContent().size());
        verify(buildingRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Buscar um prédio com ID válido")
    public void findBuildingById_WithValidId_ShouldReturnBuilding() {
        Building building1 = Instancio.create(Building.class);
        BuildingResponseDTO buildingResponseDTO = Instancio.create(BuildingResponseDTO.class);
        Long validId = 1L;

        when(buildingRepository.findById(validId)).thenReturn(Optional.of(building1));
        when(buildingMapper.toResponseDTO(building1)).thenReturn(buildingResponseDTO);

        BuildingResponseDTO result = buildingService.getBuildingById(validId);

        assertEquals(buildingResponseDTO, result);
        verify(buildingRepository).findById(validId);
    }

    @Test
    @DisplayName("Buscar um prédio com ID inválido")
    public void findBuildingById_WithInvalidId_ShouldThrowException() {
        Long invalidId = 9999L;
        when(buildingRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> buildingService.getBuildingById(invalidId));

        verify(buildingRepository).findById(invalidId);
    }

    @Test
    @DisplayName("Tentar atualizar prédio com endereço existente")
    public void updateBuilding_WithExistingAddress_ShouldThrowException() {
        Long buildingId = 1L;
        Address existingAddress = Instancio.create(Address.class);
        Building existingBuilding = Instancio.create(Building.class);

        BuildingRequestDTO buildingRequestDTO = Instancio.create(BuildingRequestDTO.class);

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(existingBuilding));
        when(addressMapper.toEntity(buildingRequestDTO.getAddress())).thenReturn(existingAddress);
        when(buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                existingAddress.getStreet(), existingAddress.getCity(), existingAddress.getZipCode()
        )).thenReturn(Optional.of(existingBuilding));

        assertThrows(IllegalArgumentException.class,
                () -> buildingService.updateBuilding(buildingId, buildingRequestDTO));

        verify(buildingRepository, never()).save(any(Building.class));
    }

    @Test
    @DisplayName("Atualizar prédio com todos os campos válidos")
    void updateBuilding_WithValidFields_ShouldUpdateBuildingSuccessfully() {
        Long buildingId = 1L;
        BuildingRequestDTO buildingRequestDTO = Instancio.create(BuildingRequestDTO.class);
        Address address = Instancio.create(Address.class);

        Building building = Instancio.create(Building.class);
        building.setId(buildingId);
        building.setAddress(address);

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));
        when(addressMapper.toEntity(any(AddressRequestDTO.class))).thenReturn(address);
        when(buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                building.getAddress().getStreet(), building.getAddress().getCity(), building.getAddress().getZipCode()
        )).thenReturn(Optional.empty());
        when(buildingMapper.updateEntityFromRequest(buildingRequestDTO, building)).thenReturn(building);
        when(buildingRepository.save(building)).thenReturn(building);
        when(buildingMapper.toResponseDTO(building)).thenReturn(new BuildingResponseDTO());

        BuildingResponseDTO buildingResponseDTO = buildingService.updateBuilding(buildingId, buildingRequestDTO);

        assertNotNull(buildingResponseDTO);
        verify(buildingRepository).save(building);
    }

    @Test
    @DisplayName("Tentar atualizar prédio com id que não existe")
    public void updateBuilding_WithInvalidId_ShouldThrowException() {
        Long buildingId = 9999L;
        BuildingRequestDTO buildingRequestDTO = Instancio.create(BuildingRequestDTO.class);

        Building building = Instancio.create(Building.class);
        building.setAddress(Instancio.create(Address.class));

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> buildingService.updateBuilding(buildingId, buildingRequestDTO)
        );

        verify(buildingRepository).findById(buildingId);
        verify(buildingRepository, never()).save(any(Building.class));
    }

    @Test
    @DisplayName("Excluir prédio com ID válido")
    public void deleteBuildingById_WithValidId_ShouldDeleteBuildingSuccessfully() {
        Long buildingId = 1L;

        when(buildingRepository.findById(buildingId)).thenReturn(
                Optional.of(Instancio.create(Building.class))
        );
        when(buildingRepository.existsById(buildingId)).thenReturn(true);

        buildingService.deleteBuilding(buildingId);

        verify(buildingRepository).deleteById(buildingId);
    }

    @Test
    @DisplayName("Tentar excluir prédio com ID inválido")
    public void deleteBuildingById_WithInvalidId_ShouldThrowException() {
        Long buildingId = 9999L;

        when(buildingRepository.existsById(buildingId)).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> buildingService.deleteBuilding(buildingId)
        );

        verify(buildingRepository, never()).deleteById(buildingId);
    }
}
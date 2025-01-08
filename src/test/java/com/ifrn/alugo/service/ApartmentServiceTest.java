package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.entity.Apartment;
import com.ifrn.alugo.entity.Building;
import com.ifrn.alugo.mappers.ApartmentMapper;
import com.ifrn.alugo.repository.ApartmentRepository;
import com.ifrn.alugo.repository.BuildingRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private ApartmentMapper apartmentMapper;

    private AutoCloseable closeable;

    @InjectMocks
    private ApartmentService apartmentService;

    @BeforeEach
    void setUp() {
       closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void destroy() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Cria um apartamento com dados válidos com sucesso")
    public void testCreateApartment_WithValidData_ShouldCreateApartmentSuccessfully() {
        ApartmentRequestDTO apartmentRequestDTO = Instancio.create(ApartmentRequestDTO.class);
        Apartment apartment = Instancio.create(Apartment.class);
        ApartmentResponseDTO apartmentResponseDTO = Instancio.create(ApartmentResponseDTO.class);
        Building building = Instancio.create(Building.class);

        when(buildingRepository.findById(1L)).thenReturn(Optional.of(building));
        when(apartmentMapper.toEntity(apartmentRequestDTO)).thenReturn(apartment);
        when(apartmentRepository.save(apartment)).thenReturn(apartment);
        when(apartmentMapper.toResponseDTO(apartment)).thenReturn(apartmentResponseDTO);

        ApartmentResponseDTO createdApartment = apartmentService.createApartment(apartmentRequestDTO);

        assertEquals(apartmentResponseDTO, createdApartment);

        verify(buildingRepository).findById(apartmentRequestDTO.getBuildingId());
        verify(apartmentMapper).toEntity(apartmentRequestDTO);
        verify(apartmentRepository).save(apartment);
        verify(apartmentMapper).toResponseDTO(apartment);
    }

}
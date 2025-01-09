package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.entity.Apartment;
import com.ifrn.alugo.entity.Building;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.ApartmentMapper;
import com.ifrn.alugo.repository.ApartmentRepository;
import com.ifrn.alugo.repository.BuildingRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
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
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Lista todos os apartamentos")
    public void testListApartments_ShouldListApartmentsSuccessfully() {
        Apartment apartment1 = Instancio.create(Apartment.class);

        List<Apartment> apartments = List.of(apartment1);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Apartment> apartmentPage = new PageImpl<>(apartments, pageable, apartments.size());
        ApartmentResponseDTO apartmentResponseDTO = Instancio.create(ApartmentResponseDTO.class);

        when(apartmentRepository.findAll(pageable)).thenReturn(apartmentPage);
        when(apartmentMapper.toResponseDTO(apartment1)).thenReturn(apartmentResponseDTO);

        Page<ApartmentResponseDTO> result = apartmentService.getAllApartments(pageable);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("Buscar apartamento com id válido")
    public void testFindApartmentById_ShouldReturnApartmentSuccessfully() {
        Apartment apartment1 = Instancio.create(Apartment.class);
        ApartmentResponseDTO apartmentResponseDTO = Instancio.create(ApartmentResponseDTO.class);

        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(apartment1));
        when(apartmentMapper.toResponseDTO(apartment1)).thenReturn(apartmentResponseDTO);

        ApartmentResponseDTO result = apartmentService.getApartmentById(1L);

        assertEquals(apartmentResponseDTO, result);
        verify(apartmentRepository).findById(1L);
    }

    @Test
    @DisplayName("Buscar apartamento com id inválido")
    public void testFindApartmentById_ShouldThrowException() {
        long invalidId = 9999L;
        Apartment apartment1 = Instancio.create(Apartment.class);

        when(apartmentRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> apartmentService.getApartmentById(invalidId)
        );

        verify(apartmentMapper, never()).toResponseDTO(apartment1);
        verify(apartmentRepository.findById(invalidId));
    }

    @Test
    @DisplayName("Atualizar apartamento com sucesso")
    public void testUpdateApartment_ShouldUpdateApartmentSuccessfully() {
        Apartment apartment1 = Instancio.create(Apartment.class);
        ApartmentRequestDTO apartmentRequestDTO = Instancio.create(ApartmentRequestDTO.class);
        ApartmentResponseDTO apartmentResponseDTO = Instancio.create(ApartmentResponseDTO.class);

        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(apartment1));
        when(apartmentMapper.updateEntityFromRequest(apartmentRequestDTO, apartment1)).thenReturn(apartment1);
        when(apartmentRepository.save(apartment1)).thenReturn(apartment1);
        when(apartmentMapper.toResponseDTO(apartment1)).thenReturn(apartmentResponseDTO);

        assertNotNull(apartmentResponseDTO);

        verify(apartmentRepository).save(apartment1);
    }

    @Test
    @DisplayName("Atualizar apartamento com id inválido")
    public void testUpdateApartment_WithInvalidId_ShouldThrowException() {
        ApartmentRequestDTO apartmentRequestDTO = Instancio.create(ApartmentRequestDTO.class);

        when(apartmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
          ResourceNotFoundException.class,
                () -> apartmentService.updateApartment(1L, apartmentRequestDTO)
        );

        verify(apartmentRepository, never()).findById(1L);
        verify(apartmentRepository, never()).save(any(Apartment.class));
    }

    @Test
    @DisplayName("Deletar apartamento com id válido")
    public void testDeleteApartmentById_ShouldDeleteApartmentSuccessfully() {
        when(apartmentRepository.existsById(1L)).thenReturn(true);

        apartmentService.deleteApartment(1L);

        verify(apartmentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deletar apartamento com id inválido")
    public void testDeleteApartment_WithInvalidId_ShouldThrowException() {
        long invalidId = 9999L;
        when(apartmentRepository.existsById(invalidId)).thenReturn(false);

        apartmentService.deleteApartment(invalidId);

        assertThrows(
                ResourceNotFoundException.class,
                () -> apartmentService.deleteApartment(invalidId)
        );

        verify(apartmentRepository, never()).deleteById(invalidId);
    }
}
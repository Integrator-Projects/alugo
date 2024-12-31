package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.entity.Apartment;
import com.ifrn.alugo.entity.Building;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.ApartmentMapper;
import com.ifrn.alugo.repository.ApartmentRepository;
import com.ifrn.alugo.repository.BuildingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentMapper apartmentMapper;
    private final BuildingRepository buildingRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, ApartmentMapper apartmentMapper, BuildingRepository buildingRepository) {
        this.apartmentRepository = apartmentRepository;
        this.apartmentMapper = apartmentMapper;
        this.buildingRepository = buildingRepository;
    }

    public ApartmentResponseDTO createApartment(ApartmentRequestDTO apartmentRequestDTO) {
        Building building = buildingRepository.findById(apartmentRequestDTO.getBuildingId())
                .orElseThrow(() -> new ResourceNotFoundException("Building with id " + apartmentRequestDTO.getBuildingId() + " not found"));

        Apartment apartment = apartmentMapper.toEntity(apartmentRequestDTO);
        apartment.setBuilding(building);

        return apartmentMapper.toResponseDTO(apartmentRepository.save(apartment));
    }

    public List<ApartmentResponseDTO> getAllApartments() {
        return apartmentRepository.findAll()
                .stream()
                .map(apartmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ApartmentResponseDTO getApartmentById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found with id " + id + " not found"));
        return apartmentMapper.toResponseDTO(apartment);
    }

    public ApartmentResponseDTO updateApartment(Long id, ApartmentRequestDTO apartmentRequestDTO) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found with id " + id + " not found"));

        apartment = apartmentMapper.updateEntityFromRequest(apartmentRequestDTO, apartment);

        return apartmentMapper.toResponseDTO(apartmentRepository.save(apartment));
    }

    public void deleteApartment(Long id) {
        if (!apartmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Apartment not found with id " + id + " not found");
        }

        apartmentRepository.deleteById(id);
    }
}

package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.BuildingRequestDTO;
import com.ifrn.alugo.dto.BuildingResponseDTO;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.Building;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.AddressMapper;
import com.ifrn.alugo.mappers.BuildingMapper;
import com.ifrn.alugo.mappers.HouseMapper;
import com.ifrn.alugo.repository.BuildingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final AddressMapper addressMapper;

    public BuildingService(BuildingRepository buildingRepository, BuildingMapper buildingMapper, HouseMapper houseMapper, AddressMapper addressMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
        this.addressMapper = addressMapper;
    }

    public BuildingResponseDTO createBuilding(BuildingRequestDTO buildingRequestDTO) {
        Address address = addressMapper.toEntity(buildingRequestDTO.getAddress());

        Optional<Building> existingBuilding = buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        );

        if (existingBuilding.isPresent()) {
            throw new IllegalArgumentException("The address is already assigned to another building.");
        }

        Building building = buildingMapper.toEntity(buildingRequestDTO);

        return buildingMapper.toResponseDTO(buildingRepository.save(building));
    }

    public Page<BuildingResponseDTO> getAllBuildings(Pageable pageable) {
        return buildingRepository.findAll(pageable).map(buildingMapper::toResponseDTO);
    }

    public BuildingResponseDTO getBuildingById(Long id) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building with id " + id + " not found"));
        return buildingMapper.toResponseDTO(building);
    }

    public BuildingResponseDTO updateBuilding(Long id, BuildingRequestDTO buildingRequestDTO) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Building with id " + id + " not found"));

        Address address = addressMapper.toEntity(buildingRequestDTO.getAddress());
        Optional<Building> existingBuilding = buildingRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        );

        if (existingBuilding.isPresent()) {
            throw new IllegalArgumentException("The address is already assigned to another building.");
        }

        building = buildingMapper.updateEntityFromRequest(buildingRequestDTO, building);
        return buildingMapper.toResponseDTO(buildingRepository.save(building));
    }

    public void deleteBuilding(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Building with id " + id + " not found");
        }
        buildingRepository.deleteById(id);
    }
}

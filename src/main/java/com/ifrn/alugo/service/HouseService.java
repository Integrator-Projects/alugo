package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.AddressRequestDTO;
import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.entity.Address;
import com.ifrn.alugo.entity.House;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.AddressMapper;
import com.ifrn.alugo.mappers.HouseMapper;
import com.ifrn.alugo.repository.HouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseService {
    private final HouseMapper houseMapper;
    private final HouseRepository houseRepository;
    private final AddressMapper addressMapper;

    public HouseService(HouseMapper houseMapper, HouseRepository houseRepository, AddressMapper addressMapper) {
        this.houseMapper = houseMapper;
        this.houseRepository = houseRepository;
        this.addressMapper = addressMapper;
    }

    public HouseResponseDTO createHouse(HouseRequestDTO houseRequestDTO) {
        AddressRequestDTO addressRequestDTO = houseRequestDTO.getAddress();
        Address address = addressMapper.toEntity(addressRequestDTO);

        Optional<House> existingHouse = houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
                address.getStreet(), address.getCity(), address.getZipCode()
        );

        if (existingHouse.isPresent()) {
            throw new IllegalArgumentException("The address is already assigned to another house.");
        }

        House house = houseMapper.toEntity(houseRequestDTO);
        houseRepository.save(house);
        return houseMapper.toResponseDTO(house);
    }

    public Page<HouseResponseDTO> getAllHouses(Pageable pageable) {
        return houseRepository.findAll(pageable)
                .map(houseMapper::toResponseDTO);
    }

    public HouseResponseDTO findHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));
        return houseMapper.toResponseDTO(house);
    }

    public HouseResponseDTO updateHouse(Long id, HouseRequestDTO houseRequestDTO) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));

        Address address = addressMapper.toEntity(houseRequestDTO.getAddress());
        Optional<House> existingHouse = houseRepository.findByAddressStreetAndAddressCityAndAddressZipCode(
          address.getStreet(), address.getCity(), address.getZipCode()
        );

        if (existingHouse.isPresent()) {
            throw new IllegalArgumentException("The address is already assigned to another house.");
        }

        house = houseMapper.updateEntityFromRequest(houseRequestDTO, house);
        return houseMapper.toResponseDTO(houseRepository.saveAndFlush(house));
    }

    public void deleteHouse(Long id) {
        if (!houseRepository.existsById(id)) {
            throw new ResourceNotFoundException("House with id " + id + " not found");
        }
        houseRepository.deleteById(id);
    }
}

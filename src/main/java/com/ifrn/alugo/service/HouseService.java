package com.ifrn.alugo.service;

import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.entity.House;
import com.ifrn.alugo.exceptions.ResourceNotFoundException;
import com.ifrn.alugo.mappers.HouseMapper;
import com.ifrn.alugo.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {
    private final HouseMapper houseMapper;
    private final HouseRepository houseRepository;

    public HouseService(HouseMapper houseMapper, HouseRepository houseRepository) {
        this.houseMapper = houseMapper;
        this.houseRepository = houseRepository;
    }

    public HouseResponseDTO createHouse(HouseRequestDTO houseRequestDTO) {
        House house = houseMapper.toEntity(houseRequestDTO);
        houseRepository.save(house);
        return houseMapper.toResponseDTO(house);
    }

    public List<HouseResponseDTO> getAllHouses() {
        return houseRepository.findAll()
                .stream()
                .map(houseMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public HouseResponseDTO findHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));
        return houseMapper.toResponseDTO(house);
    }

    public HouseResponseDTO updateHouse(Long id, HouseRequestDTO houseRequestDTO) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));

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

package com.ifrn.domusmanager.service;

import com.ifrn.domusmanager.dto.HouseRequestDTO;
import com.ifrn.domusmanager.dto.HouseResponseDTO;
import com.ifrn.domusmanager.entity.Address;
import com.ifrn.domusmanager.entity.House;
import com.ifrn.domusmanager.exceptions.ResourceNotFoundException;
import com.ifrn.domusmanager.mappers.HouseMapper;
import com.ifrn.domusmanager.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        House house = houseMapper.toEntityFromRequest(houseRequestDTO);
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

        houseMapper.updateEntityFromRequest(houseRequestDTO, house);
        return houseMapper.toResponseDTO(houseRepository.save(house));
    }

    public void deleteHouse(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House with id " + id + " not found"));
        houseRepository.deleteById(id);
    }
}

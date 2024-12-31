package com.ifrn.alugo.mappers;

import com.ifrn.alugo.dto.HouseRequestDTO;
import com.ifrn.alugo.dto.HouseResponseDTO;
import com.ifrn.alugo.entity.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface HouseMapper {
    @Mapping(target = "id", ignore = true)
    House toEntity(HouseRequestDTO houseRequestDTO);

    HouseResponseDTO toResponseDTO(House house);

    @Mapping(source = "houseRequestDTO.rentalPrice", target = "rentalPrice")
    @Mapping(source = "houseRequestDTO.description", target = "description")
    @Mapping(source = "houseRequestDTO.available", target = "available")
    @Mapping(source = "houseRequestDTO.numberOfBathrooms", target = "numberOfBathrooms")
    @Mapping(source = "houseRequestDTO.numberOfBedrooms", target = "numberOfBedrooms")
    @Mapping(source = "houseRequestDTO.areaInM2", target = "areaInM2")
    @Mapping(source = "houseRequestDTO.hasGarage", target = "hasGarage")
    @Mapping(source = "houseRequestDTO.address", target = "address")
    @Mapping(target = "id", ignore = true)
    House updateEntityFromRequest(HouseRequestDTO houseRequestDTO, @MappingTarget House house);
}

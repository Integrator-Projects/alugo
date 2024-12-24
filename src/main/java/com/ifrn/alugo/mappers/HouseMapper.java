package com.ifrn.domusmanager.mappers;

import com.ifrn.domusmanager.dto.HouseRequestDTO;
import com.ifrn.domusmanager.dto.HouseResponseDTO;
import com.ifrn.domusmanager.entity.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface HouseMapper {
    House toEntityFromRequest(HouseRequestDTO houseRequestDTO);
    HouseResponseDTO toResponseDTO(House house);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(HouseRequestDTO houseRequestDTO, House house);
}

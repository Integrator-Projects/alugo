package com.ifrn.alugo.mappers;

import com.ifrn.alugo.dto.BuildingRequestDTO;
import com.ifrn.alugo.dto.BuildingResponseDTO;
import com.ifrn.alugo.entity.Building;
import org.mapstruct.*;

import javax.annotation.processing.Generated;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
@Generated("mapstruct")
public interface BuildingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartments", ignore = true)
    Building toEntity(BuildingRequestDTO buildingRequestDTO);

    BuildingResponseDTO toResponseDTO(Building building);

    @Mapping(source = "buildingRequestDTO.numberOfFloors", target = "numberOfFloors")
    @Mapping(source = "buildingRequestDTO.description", target = "description")
    @Mapping(source = "buildingRequestDTO.address", target = "address")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "apartments", ignore = true)
    Building updateEntityFromRequest(BuildingRequestDTO buildingRequestDTO, @MappingTarget Building building);
}

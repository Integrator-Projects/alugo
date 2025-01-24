package com.ifrn.alugo.mappers;

import com.ifrn.alugo.dto.ApartmentRequestDTO;
import com.ifrn.alugo.dto.ApartmentResponseDTO;
import com.ifrn.alugo.entity.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.annotation.processing.Generated;

@Mapper(componentModel = "spring", uses = BuildingMapper.class)
@Generated("mapstruct")
public interface ApartmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "building.id", source = "buildingId")
    Apartment toEntity(ApartmentRequestDTO apartmentRequestDTO);

    ApartmentResponseDTO toResponseDTO(Apartment apartment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "building.id", source = "buildingId")
    Apartment updateEntityFromRequest(ApartmentRequestDTO apartmentRequestDTO, @MappingTarget Apartment apartment);
}

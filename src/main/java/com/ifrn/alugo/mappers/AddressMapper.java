package com.ifrn.alugo.mappers;

import com.ifrn.alugo.dto.AddressRequestDTO;
import com.ifrn.alugo.dto.AddressResponseDTO;
import com.ifrn.alugo.entity.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO toResponseDTO(Address address);

    @Mapping(source = "addressRequestDTO.city", target = "city")
    @Mapping(source = "addressRequestDTO.state", target = "state")
    @Mapping(source = "addressRequestDTO.zipCode", target = "zipCode")
    @Mapping(source = "addressRequestDTO.street", target = "street")
    @Mapping(source = "addressRequestDTO.neighborhood", target = "neighborhood")
    @Mapping(source = "addressRequestDTO.number", target = "number")
    @Mapping(target = "id", ignore = true)
    Address updateEntityFromRequest(AddressRequestDTO addressRequestDTO, @MappingTarget Address address);
}

package com.ifrn.domusmanager.mappers;

import com.ifrn.domusmanager.dto.AddressRequestDTO;
import com.ifrn.domusmanager.dto.AddressResponseDTO;
import com.ifrn.domusmanager.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntityFromRequest(AddressRequestDTO dto);
    AddressResponseDTO toResponseDTO(Address address);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(AddressRequestDTO dto, Address address);
}

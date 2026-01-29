package com.devsenior.cdiaz.bibliokeep.mapper;

import com.devsenior.cdiaz.bibliokeep.model.dto.UserRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.UserResponseDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "annualGoal", source = "annualGoal", defaultValue = "12")
    User toEntity(UserRequestDTO dto);

    // @Mapping(target = "password", ignore = true)
    UserResponseDTO toResponseDTO(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User entity);
}

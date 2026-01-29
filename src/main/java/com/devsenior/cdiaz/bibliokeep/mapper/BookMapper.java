package com.devsenior.cdiaz.bibliokeep.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.Book;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isLent", source = "isLent", defaultValue = "false")
    Book toEntity(BookRequestDTO dto);

    BookResponseDTO toResponseDTO(Book entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    void updateEntityFromDTO(BookRequestDTO dto, @MappingTarget Book entity);
}

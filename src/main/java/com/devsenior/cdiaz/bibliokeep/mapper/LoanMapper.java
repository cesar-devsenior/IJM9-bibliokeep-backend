package com.devsenior.cdiaz.bibliokeep.mapper;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoanRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoanResponseDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.Loan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LoanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    // @Mapping(target = "returned", defaultValue = "false")
    Loan toEntity(LoanRequestDTO dto);

    @Mapping(target = "bookId", source = "book.id")
    LoanResponseDTO toResponseDTO(Loan entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", ignore = true)
    void updateEntityFromDTO(LoanRequestDTO dto, @MappingTarget Loan entity);
}

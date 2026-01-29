package com.devsenior.cdiaz.bibliokeep.model.dto;

import java.util.List;
import java.util.UUID;

import com.devsenior.cdiaz.bibliokeep.model.entity.BookStatus;

public record BookResponseDTO(
        Long id,
        UUID ownerId,
        String isbn,
        String title,
        List<String> authors,
        String description,
        String thumbnail,
        BookStatus status,
        Integer rating,
        Boolean isLent
) {
}

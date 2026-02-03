package com.devsenior.cdiaz.bibliokeep.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(
    @JsonProperty("access_token")
    String accessToken,

    String type) {
    
}

package com.devsenior.cdiaz.bibliokeep.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotEmpty(message = "El correo electrónico es un valor obligatorio")
        @Email(message = "El correo electrónico tiene un formato inválido")
        String email,

        @NotEmpty(message = "La clave es un valor obligatorio") 
        @Size(min = 6, message = "La clave debe contener minino 6 caracteres")
        String password) {

}

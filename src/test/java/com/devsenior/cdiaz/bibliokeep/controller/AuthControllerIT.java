package com.devsenior.cdiaz.bibliokeep.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoginRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoginResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import tools.jackson.databind.ObjectMapper;

// @SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

//     @Test
    @DisplayName("Inicio de sesion con usuario y contrasena validos")
    public void loginSuccessMockMvc() throws JsonProcessingException, Exception {
        // Arrange
        var request = new LoginRequestDTO(
                "cdiaz@test.com",
                "cdiaz123");

        // Act
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var response = mapper.readValue(result.getResponse().getContentAsString(),
                LoginResponseDTO.class);

        // Assert
        assertNotNull(response);
        assertNotNull(response.accessToken());
    }

//     @Test
    @DisplayName("Inicio de sesion falla con usuario no existente")
    public void loginFailsWithNonExistentUser() throws Exception {
        // Arrange
        var request = new LoginRequestDTO("nonexistent@test.com", "password123");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

//     @Test
    @DisplayName("Inicio de sesion falla con campos vacios")
    public void loginFailsWithEmptyFields() throws Exception {
        // Arrange
        var request = new LoginRequestDTO("", "");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}

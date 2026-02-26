package com.devsenior.cdiaz.bibliokeep.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devsenior.cdiaz.bibliokeep.model.dto.BookRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.BookResponseDTO;
import com.devsenior.cdiaz.bibliokeep.model.entity.BookStatus;
import com.devsenior.cdiaz.bibliokeep.model.vo.JwtUser;

import tools.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void createBookWithoutAuth() throws Exception {
        var request = new BookRequestDTO(
                null,
                "1234567890",
                "Prueba de Libro",
                null,
                null,
                null,
                BookStatus.DESEADO,
                1,
                null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void createBookAsAdmin() throws Exception {
        var request = new BookRequestDTO(
                null,
                "1234567890",
                "Prueba de Libro",
                null,
                null,
                null,
                BookStatus.DESEADO,
                1,
                null);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .with(SecurityMockMvcRequestPostProcessors.authentication(getAuthenticationAdmin()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        var response = mapper.readValue(result.getResponse().getContentAsString(), BookResponseDTO.class);

        assertNotNull(response);
        assertNotNull(response.id());
    }

    private Authentication getAuthenticationAdmin() {
        SecurityContextHolder.clearContext();

        var subject = "cdiaz@example.com";
        var userId = "e7b1c9d2-3f4a-4b6c-9d8e-0a1b2c3d4e5f";
        var roles = List.of("ROLE_ADMIN");

        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        var principal = new JwtUser(subject, userId, authorities);
        var auth = new UsernamePasswordAuthenticationToken(principal, "", authorities);

        return auth;
    }
}

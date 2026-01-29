package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.UserRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(UUID id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO);

    void deleteUser(UUID id);
}

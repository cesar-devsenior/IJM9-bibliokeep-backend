package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsenior.cdiaz.bibliokeep.mapper.UserMapper;
import com.devsenior.cdiaz.bibliokeep.model.dto.UserRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.UserResponseDTO;
import com.devsenior.cdiaz.bibliokeep.repository.RoleRepository;
import com.devsenior.cdiaz.bibliokeep.repository.UserRepository;
import com.devsenior.cdiaz.bibliokeep.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.email())) {
            throw new RuntimeException("El email ya está registrado");
        }

        var user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() != null) {
            var roles = user.getRoles().stream()
                    .map(r -> roleRepository.findByName(r.getName()).orElse(null))
                    .filter(r -> r != null)
                    .toList();

            user.setRoles(roles);
        }

        var savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        if (userRepository.existsByEmail(userRequestDTO.email()) && !user.getEmail().equals(userRequestDTO.email())) {
            throw new RuntimeException("El email ya está registrado");
        }

        userMapper.updateEntityFromDTO(userRequestDTO, user);
        if (userRequestDTO.password() != null) {
            user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        }
        // if (user.getRoles() != null) {
        //     var roles = user.getRoles().stream()
        //             .map(r -> roleRepository.findByName(r.getName()).orElse(null))
        //             .filter(r -> r != null)
        //             .toList();

        //     user.setRoles(roles);
        // }

        var updatedUser = userRepository.saveAndFlush(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}

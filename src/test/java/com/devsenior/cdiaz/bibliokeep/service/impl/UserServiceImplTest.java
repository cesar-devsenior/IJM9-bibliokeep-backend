package com.devsenior.cdiaz.bibliokeep.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devsenior.cdiaz.bibliokeep.mapper.UserMapper;
import com.devsenior.cdiaz.bibliokeep.repository.RoleRepository;
import com.devsenior.cdiaz.bibliokeep.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void testCreateUser() {

    }

    @Test
    void testDeleteUser() {

    }

    @Test
    void testGetAllUsers() {

    }

    @Test
    void testGetUserById() {

    }

    @Test
    void testUpdateUser() {

    }
}

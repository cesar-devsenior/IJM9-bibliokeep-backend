package com.devsenior.cdiaz.bibliokeep.service;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoginRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoginResponseDTO;

public interface AuthService {
    
    LoginResponseDTO login(LoginRequestDTO body);
}

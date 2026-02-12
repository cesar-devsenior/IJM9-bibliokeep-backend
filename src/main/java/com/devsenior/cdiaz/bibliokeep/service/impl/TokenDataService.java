package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;

import com.devsenior.cdiaz.bibliokeep.model.vo.JwtUser;

public abstract class TokenDataService {
    
    protected UUID getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof JwtUser jwt) {
            return UUID.fromString(jwt.getUserId());
        }

        return null;
    }
}

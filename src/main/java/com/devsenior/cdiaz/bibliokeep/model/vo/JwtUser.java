package com.devsenior.cdiaz.bibliokeep.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtUser implements UserDetails {
    private final String username;
    private final String userId;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public @Nullable String getPassword() {
        return "";
    }
}
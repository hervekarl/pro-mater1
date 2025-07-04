package com.herve.pro.intergiciel.Authentification.DTO.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

}
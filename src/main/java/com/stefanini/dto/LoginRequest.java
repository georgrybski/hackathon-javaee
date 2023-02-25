package com.stefanini.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}

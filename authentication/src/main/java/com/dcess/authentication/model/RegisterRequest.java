package com.dcess.authentication.model;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
}

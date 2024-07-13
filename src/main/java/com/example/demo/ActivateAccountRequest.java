package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivateAccountRequest {
    private String username;
    private String activationCode;
}

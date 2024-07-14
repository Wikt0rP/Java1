package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ActivateAccountRequest {
    private String username;
    private String activationCode;
}

package com.example.demo.Request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class GetCarRequest {
    private String carBrand;
    private String model;
}

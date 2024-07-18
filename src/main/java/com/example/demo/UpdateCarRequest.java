package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UpdateCarRequest {
    private String carBrand;
    private String model;
    private String vin;
    private Integer year;
}

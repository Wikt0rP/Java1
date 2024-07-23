package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carBrand;

    private String model;

    @Column(length = 17)
    private String vin;

    private Integer year;

    public Car(String carBrand, String model, String vin, Integer year) {
        this.carBrand = carBrand;
        this.model = model;
        this.vin = vin;
        this.year = year;
    }
}

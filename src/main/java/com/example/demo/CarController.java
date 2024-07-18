package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarService carService;

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody CarRequest carRequest) {
        if (carRepository.carExistsByVin(carRequest.getVin())){
            return ResponseEntity.badRequest().body("Car with this VIN already exists");
        }

        Car car = new Car(carRequest.getCarBrand(), carRequest.getModel(), carRequest.getVin(), carRequest.getYear());
        if(carService.addCarToDB(car)){
            return ResponseEntity.ok("Car added successfully");
        } else {
            return ResponseEntity.badRequest().body("Car can not be added");
        }

    }

    @GetMapping("/get")
    public ResponseEntity<List<Car>> getCar(@RequestBody GetCarRequest getCarRequest){
        List<Car> carsFiltered = new ArrayList<>();
        System.out.println("Car Brand:" + getCarRequest.getCarBrand());
        System.out.println("Car Model: "+ getCarRequest.getModel());
        carsFiltered = carService.getCarByModel(getCarRequest.getCarBrand(), getCarRequest.getModel());

        if(carsFiltered != null){
            return ResponseEntity.ok(carsFiltered);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Car> updateCar(@RequestBody UpdateCarRequest updateCarRequest){
        if(updateCarRequest.getVin().length() != 17){
           throw new Error ("VIN must have 17 characters");
        }
        Car car = new Car (updateCarRequest.getCarBrand(), updateCarRequest.getModel(), updateCarRequest.getVin(), updateCarRequest.getYear());
        Car updatedCar = carService.updateCarByVin(car);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCar(@RequestBody DeleteCarRequest deleteCarRequest){
        if(deleteCarRequest.getVin().length() != 17){
            throw new Error ("VIN must have 17 characters");
        }
        if(carService.deleteCarByVin(deleteCarRequest)){
            return ResponseEntity.ok("Car deleted successfully");
        }
        return ResponseEntity.badRequest().body("Car can not be deleted");

    }


}

package com.example.demo.Service;

import com.example.demo.Entity.Car;
import com.example.demo.Repository.CarRepository;
import com.example.demo.Request.DeleteCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Boolean addCarToDB(Car car){
        if(car.getVin().length() == 17){
            carRepository.save(car);
            return true;
        } else {
            return false;
        }
    }

    public List<Car> getCarByModel(String brandName, String model){
       if(brandName != null && model != null){
           return carRepository.getCarByModel(brandName, model);
       } else {
           return null;
       }
    }

    public Car updateCarByVin(Car car){
        Car carToUpdate = carRepository.getByVin(car.getVin());
        System.out.println("Brand" +carToUpdate.getCarBrand() + "Model" + carToUpdate.getModel() + "year " + carToUpdate.getYear() + "BIN " + carToUpdate.getVin() + "Id "+ carToUpdate.getId());
        Car newCar = new Car();
        if(car.getCarBrand() == null){
            newCar.setCarBrand(carToUpdate.getCarBrand());
        }else{
            newCar.setCarBrand(car.getCarBrand());
        }
        if(car.getModel()== null) {
            newCar.setModel(carToUpdate.getModel());
        }else{
            newCar.setModel(car.getModel());
        }
        if(car.getYear() == null) {
            newCar.setYear(carToUpdate.getYear());
        }else{
            newCar.setYear(car.getYear());
        }
        newCar.setId(carToUpdate.getId());
        newCar.setVin(carToUpdate.getVin());

        System.out.println(carRepository.updateCarByVin(car.getVin(), car.getCarBrand(), car.getModel(), car.getYear()));
        return newCar;
    }

    public Boolean deleteCarByVin(DeleteCarRequest deleteCarRequest){
        if(deleteCarRequest.getVin().length() != 17){
            return false;
        }
        return carRepository.deleteCarByVin(deleteCarRequest.getVin()) != 0;


    }
}

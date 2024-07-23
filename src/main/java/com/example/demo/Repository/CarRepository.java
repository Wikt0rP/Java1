package com.example.demo.Repository;

import com.example.demo.Entity.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Car c WHERE c.vin = ?1")
    public Boolean carExistsByVin(String vin);

    @Query("SELECT c FROM Car c WHERE c.carBrand=?1 AND c.model=?2")
    public List<Car> getCarByModel(String brandName, String model);

    @Query("SELECT c FROM Car c WHERE c.vin = ?1")
    public Car getByVin(String vin);

    @Transactional
    @Modifying
    @Query("UPDATE  Car c SET c.carBrand = ?2, c.model = ?3, c.year = ?4 WHERE c.vin = ?1")
    public int updateCarByVin(String vin, String carBrand, String model, Integer year);

    @Transactional
    @Modifying
    @Query("DELETE FROM Car c WHERE c.vin = ?1")
    public int deleteCarByVin(String vin);
}

package com.unow.vehicles.service;

import com.unow.vehicles.model.Vehicle;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface de contrato a los servicios de manejo de vehiculos
 */
public interface IVehicleService {

    Page<Vehicle> findAllPageAndOrder(Integer page, Integer pageSize, String orderField,String filter, String orderDirection);

    List<Vehicle> findByMake(String make);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByPlate(String plate);

    Vehicle createVehicle(Vehicle vehicle);

    Vehicle updateVehicle(Vehicle vehicle, Integer id);

    void deleteVehicle(Integer id);


}

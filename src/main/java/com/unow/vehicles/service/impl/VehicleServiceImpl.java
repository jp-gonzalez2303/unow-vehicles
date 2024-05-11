package com.unow.vehicles.service.impl;

import com.unow.vehicles.config.exceptions.DataIntegrityViolationException;
import com.unow.vehicles.config.exceptions.ResourceNotFoundException;
import com.unow.vehicles.entities.VehicleEntity;
import com.unow.vehicles.mapper.IVehicleMapper;
import com.unow.vehicles.model.Vehicle;
import com.unow.vehicles.repository.IVehicleRepository;
import com.unow.vehicles.service.IVehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Slf4j
@Service
public class VehicleServiceImpl implements IVehicleService {

    private IVehicleRepository vehicleRepository;
    private IVehicleMapper iVehicleMapper;

    @Autowired
    public VehicleServiceImpl(IVehicleRepository vehicleRepository, IVehicleMapper iVehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.iVehicleMapper = iVehicleMapper;
    }


    /**
     * Servicio de busqueda paginada y ordenamiento de Vehiculos
     *
     * @param page
     * @param pageSize
     * @param orderField
     * @param orderDirection
     * @return Page<Vehicle>
     */
    @Override
    public Page<Vehicle> findAllPageAndOrder(Integer page, Integer pageSize, String orderField, String filter, String orderDirection) {


        log.info("Inicio de proceso de busqueda, pagina: {}, tamaño de pagina: {}, campo de ordenamiento: {}, orden: {}"
                , page, pageSize, orderField, orderDirection);
        Sort sort = Sort.by(orderField);
        if (orderDirection.equalsIgnoreCase("desc"))
            sort = sort.descending();
        else
            sort = sort.ascending();

        Page<VehicleEntity> vehicleEntities = null;
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        if (filter != null && !filter.isEmpty())

            vehicleEntities = vehicleRepository.findAllPagedByFilter(filter, pageable);
        else
            vehicleEntities = vehicleRepository.findAll(pageable);

        log.info("registros encontrados: {}", vehicleEntities.getTotalPages());
        return vehicleEntities.map(iVehicleMapper::toModel);
    }

    /**
     * Servicio de busqueda en lista de todos los registros por marca
     *
     * @param make
     * @return
     */
    @Override
    public List<Vehicle> findByMake(String make) {

        log.info("Inicio de proceso de busqueda por marca,{}", make);
        List<VehicleEntity> vehicleEntities = vehicleRepository.findByMake(make);
        List<Vehicle> response= new ArrayList<>();
        for (VehicleEntity vehicleEntity : vehicleEntities){
            response.add(iVehicleMapper.toModel(vehicleEntity));
        }
        return response;
    }

    /**
     * Servicio de busqueda en lista para todos los registros por  modelo
     *
     * @param model
     * @return
     */
    @Override
    public List<Vehicle> findByModel(String model) {
        log.info("Inicio de proceso de busqueda por modelo,{}", model);
        List<VehicleEntity> vehicleEntities = vehicleRepository.findByModel(model);

        List<Vehicle> response= new ArrayList<>();
        for (VehicleEntity vehicleEntity : vehicleEntities){
            response.add(iVehicleMapper.toModel(vehicleEntity));
        }
        return response;
    }

    /**
     * Servicio de busqueda en lista para todos los registros por  placa
     *
     * @param plate
     * @return
     */
    @Override
    public List<Vehicle> findByPlate(String plate) {
        log.info("Inicio de proceso de busqueda por placa,{}", plate);
        List<VehicleEntity> vehicleEntities = vehicleRepository.findByPlateList(plate);
        List<Vehicle> response= new ArrayList<>();
        for (VehicleEntity vehicleEntity : vehicleEntities){
            response.add(iVehicleMapper.toModel(vehicleEntity));
        }
        return response;
    }

    /**
     * Servicio encargado de crear un nuevo vehicul oen el sistema
     *
     * @param vehicle
     * @return
     */
    @Override
    public Vehicle createVehicle(Vehicle vehicle) {

        log.info("Inicio proceso Crear un nuevo Vehiculo {}", vehicle);
        Optional<VehicleEntity> vehicleFind = vehicleRepository.findByPlate(vehicle.getPlate());
        if (vehicleFind.isPresent()) {
            log.info("La placa de este vehiculo ya existe");
            throw new DataIntegrityViolationException("La placa de este vehiculo ya existe");
        }

        vehicleRepository.save(iVehicleMapper.toEntity(vehicle));
        log.info("Vehiculo Almacenado ocn Exito");

        return vehicle;
    }

    /**
     * Servicio encargado de actualiar un servicio conforme al id del registro
     *
     * @param vehicle
     * @param id
     * @return
     */
    @Override
    public Vehicle updateVehicle(Vehicle vehicle, Integer id) {
        log.info("Inicia proceso de actualizacion de un vechiculo por id {}, body {}", id, vehicle);


        Optional<VehicleEntity> vehicleFind = vehicleRepository.findById(id);
        if (!vehicleFind.isPresent()) {
            log.info("El Vehiculo no existe");
            throw new ResourceNotFoundException("El Vehiculo no existe");
        }
        vehicleFind.get().setColor(vehicle.getColor());
        vehicleFind.get().setYear(vehicle.getYear());
        vehicleFind.get().setPlate(vehicle.getPlate());
        vehicleFind.get().setMake(vehicle.getMake());
        vehicleFind.get().setModel(vehicle.getModel());

        vehicleRepository.save(vehicleFind.get());
        log.info("El Vehiculo fue actualizado con exito");

        return vehicle;
    }

    /**
     * Servicio encargado de la eliminacion fisica de un registro en base de datos
     *
     * @param id
     */
    @Override
    public void deleteVehicle(Integer id) {
        log.info("Inicia proceso de eliminacion de un vechiculo por id {}", id);

        Optional<VehicleEntity> vehicleFind = vehicleRepository.findById(id);
        if (!vehicleFind.isPresent()) {
            log.info("El Vehiculo no existe");

            throw new ResourceNotFoundException("El Vehiculo no existe");
        }

        vehicleRepository.deleteById(id);
        log.info("El Vehiculo fue eliminado con exito");


    }
}
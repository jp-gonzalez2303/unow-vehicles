package com.unow.vehicles.controller;

import com.unow.vehicles.model.Vehicle;
import com.unow.vehicles.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Clase De exposicion de servicios rest para el manejo de los vehiculos de la applicacion
 */
@RestController()
@RequestMapping("/vehicle")
@CrossOrigin("*")
public class VehicleController {


    private IVehicleService vehicleService;

    @Autowired
    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Servicio rest de consulta pagina de todos los registros en la base de datos
     *
     * @param page           numero de la pagina
     * @param pageSize       cantidad de registros por pagina
     * @param orderField     campo por el cual se quiere ordenar
     * @param orderDirection orden de busqueda acendiente o descendiente
     * @return Page<Vehicle>
     */
    @GetMapping("")
    public ResponseEntity<Page<Vehicle>> findAllPageAndOrder(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam String orderField,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "asc") String orderDirection) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.findAllPageAndOrder(page, pageSize, orderField, filter,orderDirection));
    }

    @GetMapping("/make/{make}")
    public ResponseEntity<List<Vehicle>> findByMake(@PathVariable("make") String make) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.findByMake(make));
    }

    @GetMapping("/model/{model}")
    public ResponseEntity<List<Vehicle>> findByModel(@PathVariable("model") String model) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.findByModel(model));
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<Vehicle>> findByPlate(@PathVariable("plate") String plate) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(vehicleService.findByPlate(plate));
    }

    /**
     * Servicio Rest para la insercion de vehiculos en la aplicacion
     *
     * @param vehicle
     * @return
     */
    @PostMapping("")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.createVehicle(vehicle));
    }


    /**
     * Servicio rest para la actualizacion de un ehiculo por el id en la aplicacion
     *
     * @param vehicle
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@Valid @RequestBody Vehicle vehicle, @PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(vehicle, id));
    }

    /**
     * Servicio Rest para la eliminacion fisica de un registro en la base de datos
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicle> deeteVehicle(@PathVariable("id") Integer id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

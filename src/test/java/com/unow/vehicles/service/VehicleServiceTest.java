package com.unow.vehicles.service;


import com.unow.vehicles.config.exceptions.DataIntegrityViolationException;
import com.unow.vehicles.config.exceptions.ResourceNotFoundException;
import com.unow.vehicles.entities.VehicleEntity;
import com.unow.vehicles.mapper.IVehicleMapper;
import com.unow.vehicles.model.Vehicle;
import com.unow.vehicles.repository.IVehicleRepository;
import com.unow.vehicles.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VehicleServiceTest {

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private IVehicleMapper vehicleMapper;
    @Mock
    private IVehicleRepository vehicleRepository;

    @Test
    public void testFindAllPagedAsc() {

        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(VehicleEntity.builder()
                .id(1).year(2017).make("MAZDA").model("Suru").color("blue").plate("JCS217")
                .build());
        vehicleEntities.add(VehicleEntity.builder()
                .id(2).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build());
        Page<VehicleEntity> resultsMokcs = new PageImpl<>(vehicleEntities);


        when(vehicleRepository.findAll((Pageable) any())).thenReturn(resultsMokcs);
        Page<Vehicle> results = vehicleService.findAllPageAndOrder(0, 10, "plate", "", "asc");
        assertEquals(2, results.getSize());
        verify(vehicleRepository, times(1)).findAll((Pageable) any());
    }

    @Test
    public void testFindAllPagedDesc() {

        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(VehicleEntity.builder()
                .id(1).year(2013).make("ALFA ROMEO").model("G1").color("Black").plate("KMS201")
                .build());
        vehicleEntities.add(VehicleEntity.builder()
                .id(2).year(2017).make("koenigsegg").model("AGERA").color("RED").plate("PRT703")
                .build());
        Page<VehicleEntity> resultsMokcs = new PageImpl<>(vehicleEntities);


        when(vehicleRepository.findAll((Pageable) any())).thenReturn(resultsMokcs);
        Page<Vehicle> results = vehicleService.findAllPageAndOrder(0, 10, "plate", "", "desc");
        assertEquals(2, results.getSize());
        verify(vehicleRepository, times(1)).findAll((Pageable) any());
    }

    @Test
    public void testFindByMake() {

        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(VehicleEntity.builder()
                .id(1).year(2013).make("ALFA ROMEO").model("AGERA").color("Black").plate("KMS201")
                .build());
        vehicleEntities.add(VehicleEntity.builder()
                .id(2).year(2017).make("koenigsegg").model("AGERA").color("RED").plate("PRT703")
                .build());


        when(vehicleRepository.findByMake(any())).thenReturn(vehicleEntities);
        List<Vehicle> results = vehicleService.findByMake("AGERA");
        assertEquals(2, results.size());
        verify(vehicleRepository, times(1)).findByMake(any());
    }

    @Test
    public void testFindByModel() {

        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(VehicleEntity.builder()
                .id(1).year(2013).make("ALFA ROMEO").model("AGERA").color("Black").plate("KMS201")
                .build());
        vehicleEntities.add(VehicleEntity.builder()
                .id(2).year(2017).make("koenigsegg").model("AGERA").color("RED").plate("PRT703")
                .build());


        when(vehicleRepository.findByModel(any())).thenReturn(vehicleEntities);
        List<Vehicle> results = vehicleService.findByModel("AGERA");
        assertEquals(2, results.size());
        verify(vehicleRepository, times(1)).findByModel(any());
    }

    @Test
    public void testFindByPlate() {


        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(VehicleEntity.builder()
                .id(1).year(2013).make("ALFA ROMEO").model("AGERA").color("Black").plate("KMS201")
                .build());
        vehicleEntities.add(VehicleEntity.builder()
                .id(2).year(2017).make("koenigsegg").model("AGERA").color("RED").plate("PRT703")
                .build());


        when(vehicleRepository.findByPlateList(any())).thenReturn(vehicleEntities);
        List<Vehicle> results = vehicleService.findByPlate("AGERA");
        assertEquals(2, results.size());
        verify(vehicleRepository, times(1)).findByPlateList(any());
    }


    @Test
    public void testCreateSuccess() {
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(1).year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleRepository.findByPlate(any())).thenReturn(Optional.empty());
        when(vehicleRepository.save(any())).thenReturn(vehicleEntity);


        Vehicle vehicleToSave = Vehicle.builder()
                .year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();

        Vehicle result = vehicleService.createVehicle(vehicleToSave);
        assertEquals("JKL097", result.getPlate());
        verify(vehicleRepository, times(1)).findByPlate(any());
        verify(vehicleRepository, times(1)).save(any());
    }

    @Test
    public void testCreatePlateExits() {
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(1).year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleRepository.findByPlate(any())).thenReturn(Optional.of(vehicleEntity));
        Vehicle vehicleToSave = Vehicle.builder()
                .year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> vehicleService.createVehicle(vehicleToSave));
        verify(vehicleRepository, times(1)).findByPlate(any());
    }

    @Test
    public void testUpdateByIdSuccess() {

        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(1).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicleEntity));

        Vehicle vehicleToUpdate = Vehicle.builder()
                .id(1).year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();

        Vehicle results = vehicleService.updateVehicle(vehicleToUpdate, 1);
        assertEquals(1, results.getId());
        verify(vehicleRepository, times(1)).findById(any());
    }


    @Test
    public void testUpdateByIdNotFound() {


        when(vehicleRepository.findById(any())).thenReturn(Optional.empty());

        Vehicle vehicleToUpdate = Vehicle.builder()
                .id(1).year(2010).make("bmw").model("S3").color("RED").plate("JKL097")
                .build();
        assertThrows(ResourceNotFoundException.class,
                () -> vehicleService.updateVehicle(vehicleToUpdate, 1));

        verify(vehicleRepository, times(1)).findById(any());
    }


    @Test
    public void testDeleteByIdSuccess() {

        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(1).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();
        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicleEntity));

        doNothing().when(vehicleRepository).deleteById(any());
        vehicleService.deleteVehicle(1);

        verify(vehicleRepository, times(1)).deleteById(any());
    }


    @Test
    public void testDeleteByIdNotFound() {


        when(vehicleRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> vehicleService.deleteVehicle(1));

        verify(vehicleRepository, times(1)).findById(any());
    }
}

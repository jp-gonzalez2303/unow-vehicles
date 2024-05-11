package com.unow.vehicles.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.unow.vehicles.config.exceptions.DataIntegrityViolationException;
import com.unow.vehicles.config.exceptions.ResourceNotFoundException;
import com.unow.vehicles.mapper.IVehicleMapper;
import com.unow.vehicles.model.Vehicle;
import com.unow.vehicles.service.IVehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VehicleController.class)
@ComponentScans(value = {@ComponentScan("com.unow.vehicles"), @ComponentScan("com.unow.vehicles.repository")})
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private VehicleController vehicleController;

    @MockBean
    private IVehicleService vehicleService;


    @Mock
    private IVehicleMapper vehicleMapper;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testFindAllPagedSuccess() throws Exception {
        List<Vehicle> vehicleEntities = new ArrayList<>();
        vehicleEntities.add(Vehicle.builder()
                .id(1).year(2017).make("MAZDA").model("Suru").color("blue").plate("JCS217")
                .build());
        vehicleEntities.add(Vehicle.builder()
                .id(2).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build());
        Page<Vehicle> resultsMokcs = new PageImpl<>(vehicleEntities);

        when(vehicleService.findAllPageAndOrder(any(), any(), any(), any(), any())).thenReturn(resultsMokcs);

        mockMvc.perform(get("/vehicle/?orderDirection=asc&orderField=plate&page=1&pageSize=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllPagedError() throws Exception {

        mockMvc.perform(get("/vehicle/?orderDirection=asc&orderField=plate&page=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindAllByMake() throws Exception {
        when(vehicleService.findByMake(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/vehicle/make/audi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllByModel() throws Exception {
        when(vehicleService.findByModel(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/vehicle/model/audi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllByPlate() throws Exception {
        when(vehicleService.findByPlate(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/vehicle/plate/jcs217")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testCreateVehicleSucces() throws Exception {

        Vehicle vehicle = Vehicle.builder()
                .id(2).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleService.createVehicle(any())).thenReturn(vehicle);

        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vehicle)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateVehicleErrorBody() throws Exception {
        Vehicle vehicle = Vehicle.builder()
                .make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleService.createVehicle(any())).thenThrow(new DataIntegrityViolationException("Vehiculo ya existe"));
        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vehicle)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testCreateVehicleError() throws Exception {
        Vehicle vehicle = Vehicle.builder()
                .id(2).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleService.createVehicle(any())).thenThrow(new DataIntegrityViolationException("Vehiculo ya existe"));
        mockMvc.perform(post("/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vehicle)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateVehicle() throws Exception {


        Vehicle vehicle = Vehicle.builder()
                .year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();

        when(vehicleService.createVehicle(any())).thenReturn(vehicle);

        mockMvc.perform(put("/vehicle/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vehicle)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateVehicleError() throws Exception {


        Vehicle vehicle = Vehicle.builder()
                .id(2).year(2017).make("AUDI").model("S3").color("RED").plate("JKL097")
                .build();


        when(vehicleService.updateVehicle(any(),any())).thenThrow(new ResourceNotFoundException("Vehiculo no existe"));

        mockMvc.perform(put("/vehicle/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vehicle)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteVehicle() throws Exception {


        doNothing().when(vehicleService).deleteVehicle(any());

        mockMvc.perform(delete("/vehicle/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.unow.vehicles.mapper;

import com.unow.vehicles.entities.VehicleEntity;
import com.unow.vehicles.model.Vehicle;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVehicleMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "make", target = "make")
    @Mapping(source = "model", target = "model")
    @Mapping(source = "plate", target = "plate")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "year", target = "year")
    Vehicle toModel(VehicleEntity vehicle);

    @InheritInverseConfiguration
    VehicleEntity toEntity(Vehicle dto);





}

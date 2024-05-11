package com.unow.vehicles.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle {

    private Integer id;

    @NotNull(message = "El campo make es obligatorio")
    @NotEmpty(message = "El campo make no puede estar vacío")
    private String make;

    @NotNull(message = "El campo model es obligatorio")
    @NotEmpty(message = "El campo model no puede estar vacío")
    private String model;

    @NotNull(message = "El campo plate es obligatorio")
    @NotEmpty(message = "El campo plate no puede estar vacío")
    private String plate;

    @NotNull(message = "El campo color es obligatorio")
    @NotEmpty(message = "El campo color no puede estar vacío")
    private String color;

    @NotNull(message = "El campo year es obligatorio")
    private Integer year;

}

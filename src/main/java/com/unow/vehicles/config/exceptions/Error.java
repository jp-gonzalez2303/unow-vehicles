package com.unow.vehicles.config.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class Error {
    private Integer code;
    private String message;

}

package com.ndourcodeur.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {

    private Long id;

    @NotBlank(message = "This field is required!")
    private String name;

    @Min(0)
    private Double price;

    @NotBlank(message = "This field is required!")
    private String brand;

    @NotBlank(message = "This field is required!")
    private String model;

    @NotBlank(message = "This field is required!")
    private String imageUrl;

    private Date dateReleased;

    private Boolean isInStock;

    private String userEmail;
}
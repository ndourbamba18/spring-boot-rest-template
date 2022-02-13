package com.ndourcodeur.laptopservice.dto;

import com.ndourcodeur.laptopservice.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopRequest {

    private Long id;

    @NotBlank(message = "This field is required!")
    private String lapTopName;

    @Min(0)
    private Double lapTopPrice;

    @NotBlank(message = "This field is required!")
    private String lapTopBrand;


    @Enumerated(EnumType.STRING)
    private Model model;

    @NotBlank(message = "This field is required!")
    private String imageUrl;

    private Date dateReleased;

    private Boolean isInStock;

    private String description;

    private String userEmail;
}
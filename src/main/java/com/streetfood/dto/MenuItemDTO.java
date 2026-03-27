package com.streetfood.dto;
import lombok.Data;

@Data
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean isAvailable;
    private Long vendorId;
}
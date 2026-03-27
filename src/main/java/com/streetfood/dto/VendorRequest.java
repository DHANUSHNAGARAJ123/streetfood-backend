package com.streetfood.dto;
import lombok.Data;

@Data
public class VendorRequest {
    private String shopName;
    private String city;
    private String address;
    private String category;
    private String description;
    private String photoUrl;
    private String openingTime;
    private String closingTime;
    private String priceRange;
    private Double lat;
    private Double lng;
}
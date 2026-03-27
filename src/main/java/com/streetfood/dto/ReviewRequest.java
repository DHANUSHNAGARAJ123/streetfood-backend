package com.streetfood.dto;
import lombok.Data;

@Data
public class ReviewRequest {
    private Long vendorId;
    private Integer rating;
    private String comment;
}
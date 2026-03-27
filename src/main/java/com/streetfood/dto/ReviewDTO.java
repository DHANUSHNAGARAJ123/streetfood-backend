package com.streetfood.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private Long vendorId;
    private String vendorName;
    private Long customerId;
    private String customerName;
    private int rating;
    private String comment;
    private Boolean approved;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long v) { this.vendorId = v; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String v) { this.vendorName = v; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long c) { this.customerId = c; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String c) { this.customerName = c; }
    public int getRating() { return rating; }
    public void setRating(int r) { this.rating = r; }
    public String getComment() { return comment; }
    public void setComment(String c) { this.comment = c; }
    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean a) { this.approved = a; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}
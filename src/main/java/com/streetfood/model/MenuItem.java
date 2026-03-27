package com.streetfood.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id")
    @JsonIgnoreProperties({"menuItems","reviews","user","hibernateLazyInitializer","handler"})
    private Vendor vendor;

    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean isAvailable = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor v) { this.vendor = v; }
    public String getName() { return name; }
    public void setName(String s) { this.name = s; }
    public String getDescription() { return description; }
    public void setDescription(String s) { this.description = s; }
    public String getCategory() { return category; }
    public void setCategory(String s) { this.category = s; }
    public Double getPrice() { return price; }
    public void setPrice(Double p) { this.price = p; }
    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean b) { this.isAvailable = b; }
}
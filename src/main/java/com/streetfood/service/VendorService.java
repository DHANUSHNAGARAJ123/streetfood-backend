package com.streetfood.service;

import com.streetfood.dto.VendorDTO;
import com.streetfood.model.Vendor;
import com.streetfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public List<VendorDTO> getNearbyVendors(Double lat, Double lng, double radius) {
        if (lat == null || lng == null) {
            return vendorRepository.findByStatus("APPROVED")
                .stream().map(this::toDTO).collect(Collectors.toList());
        }
        return vendorRepository.findNearbyVendors(lat, lng, radius)
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<VendorDTO> getVendorsByCity(String city) {
        return vendorRepository.findByCityIgnoreCaseAndStatus(city, "APPROVED")
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<VendorDTO> getAllApprovedVendors() {
        return vendorRepository.findByStatus("APPROVED")
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public VendorDTO getVendorByUserId(Long userId) {
        return vendorRepository.findByUserId(userId)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("Vendor not found for user"));
    }

    public VendorDTO getVendorByEmail(String email) {
        return vendorRepository.findByOwnerEmail(email)
            .map(this::toDTO)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public VendorDTO toggleLive(Long userId) {
        Vendor vendor = vendorRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Vendor not found"));
        if (!"APPROVED".equals(vendor.getStatus()))
            throw new RuntimeException("Admin approval required");
        vendor.setIsLive(!Boolean.TRUE.equals(vendor.getIsLive()));
        return toDTO(vendorRepository.save(vendor));
    }

    public VendorDTO toDTO(Vendor v) {
        VendorDTO dto = new VendorDTO();
        dto.setId(v.getId());
        dto.setShopName(v.getShopName());
        dto.setOwnerName(v.getOwnerName());
        dto.setOwnerEmail(v.getOwnerEmail());
        dto.setOwnerPhone(v.getOwnerPhone());
        dto.setCity(v.getCity());
        dto.setAddress(v.getAddress());
        dto.setCategory(v.getCategory());
        dto.setDescription(v.getDescription());
        dto.setPhotoUrl(v.getPhotoUrl());
        dto.setOpeningTime(v.getOpeningTime());
        dto.setClosingTime(v.getClosingTime());
        dto.setPriceRange(v.getPriceRange());
        dto.setStatus(v.getStatus());
        dto.setIsLive(v.getIsLive());
        dto.setLat(v.getLat());
        dto.setLng(v.getLng());
        dto.setAvgRating(v.getAvgRating());
        dto.setTotalReviews(v.getTotalReviews());
        return dto;
    }
}
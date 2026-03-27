package com.streetfood.controller;

import com.streetfood.dto.ReviewDTO;
import com.streetfood.dto.VendorDTO;
import com.streetfood.model.MenuItem;
import com.streetfood.model.User;
import com.streetfood.model.Vendor;
import com.streetfood.repository.MenuItemRepository;
import com.streetfood.repository.ReviewRepository;
import com.streetfood.repository.UserRepository;
import com.streetfood.repository.VendorRepository;
import com.streetfood.service.ReviewService;
import com.streetfood.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorManagementController {

    private final VendorRepository vendorRepository;
    private final MenuItemRepository menuItemRepository;
    private final ReviewRepository reviewRepository;
    private final VendorService vendorService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    private Vendor getVendor(Authentication auth) {
        String email = auth.getName();
        // Try by ownerEmail first, then by userId
        return vendorRepository.findByOwnerEmail(email)
            .orElseGet(() -> {
                User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
                return vendorRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Vendor profile not found. Please contact admin."));
            });
    }

    @GetMapping("/my-shop")
    public ResponseEntity<VendorDTO> getMyShop(Authentication auth) {
        return ResponseEntity.ok(vendorService.toDTO(getVendor(auth)));
    }

    @PutMapping("/toggle-live")
    public ResponseEntity<VendorDTO> toggleLive(Authentication auth) {
        Vendor vendor = getVendor(auth);
        if (!"APPROVED".equals(vendor.getStatus()))
            throw new RuntimeException("Admin approval required before going live");
        vendor.setIsLive(!Boolean.TRUE.equals(vendor.getIsLive()));
        return ResponseEntity.ok(vendorService.toDTO(vendorRepository.save(vendor)));
    }

    @PutMapping("/shop")
    public ResponseEntity<VendorDTO> updateShop(@RequestBody VendorDTO dto, Authentication auth) {
        Vendor vendor = getVendor(auth);
        if (dto.getShopName()    != null) vendor.setShopName(dto.getShopName());
        if (dto.getCity()        != null) vendor.setCity(dto.getCity());
        if (dto.getAddress()     != null) vendor.setAddress(dto.getAddress());
        if (dto.getCategory()    != null) vendor.setCategory(dto.getCategory());
        if (dto.getDescription() != null) vendor.setDescription(dto.getDescription());
        if (dto.getPhotoUrl()    != null) vendor.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getOpeningTime() != null) vendor.setOpeningTime(dto.getOpeningTime());
        if (dto.getClosingTime() != null) vendor.setClosingTime(dto.getClosingTime());
        if (dto.getPriceRange()  != null) vendor.setPriceRange(dto.getPriceRange());
        if (dto.getLat()         != null) vendor.setLat(dto.getLat());
        if (dto.getLng()         != null) vendor.setLng(dto.getLng());
        return ResponseEntity.ok(vendorService.toDTO(vendorRepository.save(vendor)));
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuItem>> getMenu(Authentication auth) {
        Vendor vendor = getVendor(auth);
        return ResponseEntity.ok(menuItemRepository.findByVendorId(vendor.getId()));
    }

    @PostMapping("/menu")
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem item, Authentication auth) {
        Vendor vendor = getVendor(auth);
        item.setVendor(vendor);
        if (item.getIsAvailable() == null) item.setIsAvailable(true);
        return ResponseEntity.ok(menuItemRepository.save(item));
    }

    @PutMapping("/menu/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updated) {
        MenuItem item = menuItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        if (updated.getName()        != null) item.setName(updated.getName());
        if (updated.getDescription() != null) item.setDescription(updated.getDescription());
        if (updated.getCategory()    != null) item.setCategory(updated.getCategory());
        if (updated.getPrice()       != null) item.setPrice(updated.getPrice());
        if (updated.getIsAvailable() != null) item.setIsAvailable(updated.getIsAvailable());
        return ResponseEntity.ok(menuItemRepository.save(item));
    }

    @DeleteMapping("/menu/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(Authentication auth) {
        Vendor vendor = getVendor(auth);
        return ResponseEntity.ok(
            reviewRepository.findByVendorId(vendor.getId())
                .stream().map(reviewService::toDTO).collect(Collectors.toList()));
    }
}
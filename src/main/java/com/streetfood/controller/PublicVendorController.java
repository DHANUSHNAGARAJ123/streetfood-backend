package com.streetfood.controller;

import com.streetfood.dto.ReviewDTO;
import com.streetfood.dto.VendorDTO;
import com.streetfood.model.MenuItem;
import com.streetfood.repository.*;
import com.streetfood.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicVendorController {

    private final VendorRepository vendorRepo;
    private final MenuItemRepository menuRepo;
    private final ReviewRepository reviewRepo;
    private final ReviewService reviewService;
    private final VendorService vendorService;

    @GetMapping("/vendors")
    public ResponseEntity<List<VendorDTO>> all() {
        return ResponseEntity.ok(
            vendorRepo.findByStatus("APPROVED").stream()
                .map(vendorService::toDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/vendors/search")
    public ResponseEntity<List<VendorDTO>> searchByMenuItem(@RequestParam String q) {
        String query = q.trim().toLowerCase();
        if (query.isEmpty()) {
            return ResponseEntity.ok(
                vendorRepo.findByStatus("APPROVED").stream()
                    .map(vendorService::toDTO).collect(Collectors.toList())
            );
        }

        // Vendors whose menu items match
        List<Long> menuVendorIds = menuRepo.findAll().stream()
            .filter(item ->
                item.getName() != null &&
                item.getName().toLowerCase().contains(query) &&
                item.getVendor() != null &&
                "APPROVED".equals(item.getVendor().getStatus())
            )
            .map(item -> item.getVendor().getId())
            .distinct()
            .collect(Collectors.toList());

        // Vendors whose name/category matches
        List<Long> nameVendorIds = vendorRepo.findByStatus("APPROVED").stream()
            .filter(v ->
                (v.getShopName() != null && v.getShopName().toLowerCase().contains(query)) ||
                (v.getCategory() != null && v.getCategory().toLowerCase().contains(query))
            )
            .map(v -> v.getId())
            .collect(Collectors.toList());

        // Combine + deduplicate
        menuVendorIds.addAll(nameVendorIds);
        List<VendorDTO> result = menuVendorIds.stream()
            .distinct()
            .map(vid -> vendorRepo.findById(vid).orElse(null))
            .filter(v -> v != null)
            .map(vendorService::toDTO)
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/vendors/{id}")
    public ResponseEntity<VendorDTO> one(@PathVariable Long id) {
        return vendorRepo.findById(id)
            .map(v -> ResponseEntity.ok(vendorService.toDTO(v)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vendors/{id}/menu")
    public ResponseEntity<List<MenuItem>> menu(@PathVariable Long id) {
        return ResponseEntity.ok(menuRepo.findByVendorId(id));
    }

    @GetMapping("/vendors/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> reviews(@PathVariable Long id) {
        // Only approved reviews
        return ResponseEntity.ok(
            reviewRepo.findByVendorId(id).stream()
                .filter(r -> Boolean.TRUE.equals(r.getApproved()))
                .map(reviewService::toDTO)
                .collect(Collectors.toList())
        );
    }
}
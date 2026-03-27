package com.streetfood.controller;

import com.streetfood.dto.ReviewDTO;
import com.streetfood.dto.VendorDTO;
import com.streetfood.model.Review;
import com.streetfood.model.User;
import com.streetfood.model.Vendor;
import com.streetfood.repository.ReviewRepository;
import com.streetfood.repository.UserRepository;
import com.streetfood.repository.VendorRepository;
import com.streetfood.service.ReviewService;
import com.streetfood.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final ReviewRepository reviewRepository;
    private final VendorService vendorService;
    private final ReviewService reviewService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String,Object>> stats() {
        long customers = userRepository.findAll().stream().filter(u -> "CUSTOMER".equals(u.getRole())).count();
        long vendors   = userRepository.findAll().stream().filter(u -> "VENDOR".equals(u.getRole())).count();
        long pending   = vendorRepository.findByStatus("PENDING").size();
        long approved  = vendorRepository.findByStatus("APPROVED").size();
        long reviews   = reviewRepository.count();
        long live      = vendorRepository.findByIsLiveTrue().size();
        return ResponseEntity.ok(Map.of(
            "customers", customers, "vendors", vendors,
            "pending", pending, "approved", approved,
            "reviews", reviews, "live", live,
            "total", customers + vendors
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vendors")
    public ResponseEntity<List<VendorDTO>> getVendors() {
        return ResponseEntity.ok(
            vendorRepository.findAll().stream()
                .map(vendorService::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/vendors/{id}/approve")
    public ResponseEntity<VendorDTO> approveVendor(@PathVariable Long id) {
        Vendor v = vendorRepository.findById(id).orElseThrow();
        v.setStatus("APPROVED");
        return ResponseEntity.ok(vendorService.toDTO(vendorRepository.save(v)));
    }

    @PutMapping("/vendors/{id}/reject")
    public ResponseEntity<VendorDTO> rejectVendor(@PathVariable Long id) {
        Vendor v = vendorRepository.findById(id).orElseThrow();
        v.setStatus("REJECTED");
        return ResponseEntity.ok(vendorService.toDTO(vendorRepository.save(v)));
    }

    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews() {
        return ResponseEntity.ok(
            reviewRepository.findAll().stream()
                .map(reviewService::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/reviews/pending")
    public ResponseEntity<List<ReviewDTO>> getPendingReviews() {
        return ResponseEntity.ok(
            reviewRepository.findAll().stream()
                .filter(r -> !Boolean.TRUE.equals(r.getApproved()))
                .map(reviewService::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/reviews/{id}/approve")
    public ResponseEntity<ReviewDTO> approveReview(@PathVariable Long id) {
        Review r = reviewRepository.findById(id).orElseThrow();
        r.setApproved(true);
        Review saved = reviewRepository.save(r);
        // Update vendor rating after approval
        reviewService.updateVendorRating(r.getVendor());
        return ResponseEntity.ok(reviewService.toDTO(saved));
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
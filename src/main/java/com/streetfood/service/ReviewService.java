package com.streetfood.service;

import com.streetfood.dto.ReviewDTO;
import com.streetfood.model.*;
import com.streetfood.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final VendorRepository vendorRepo;
    private final UserRepository userRepo;

    public List<ReviewDTO> getApprovedByVendor(Long vendorId) {
        return reviewRepo.findByVendorId(vendorId).stream()
            .filter(r -> Boolean.TRUE.equals(r.getApproved()))
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public ReviewDTO add(String email, Long vendorId, int rating, String comment) {
        User u = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Vendor v = vendorRepo.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor not found"));
        Review r = new Review();
        r.setCustomer(u); r.setVendor(v);
        r.setRating(rating); r.setComment(comment);
        r.setApproved(false);
        return toDTO(reviewRepo.save(r));
    }

    public void updateVendorRating(Vendor v) {
        List<Review> approved = reviewRepo.findByVendorId(v.getId()).stream()
            .filter(r -> Boolean.TRUE.equals(r.getApproved()))
            .collect(Collectors.toList());
        double avg = approved.stream().mapToInt(Review::getRating).average().orElse(0.0);
        v.setAvgRating(Math.round(avg * 10.0) / 10.0);
        v.setTotalReviews(approved.size());
        vendorRepo.save(v);
    }

    public ReviewDTO toDTO(Review r) {
        ReviewDTO d = new ReviewDTO();
        d.setId(r.getId()); d.setRating(r.getRating());
        d.setComment(r.getComment()); d.setCreatedAt(r.getCreatedAt());
        d.setApproved(r.getApproved());
        if(r.getCustomer() != null) {
            d.setCustomerId(r.getCustomer().getId());
            d.setCustomerName(r.getCustomer().getName());
        }
        if(r.getVendor() != null) {
            d.setVendorId(r.getVendor().getId());
            d.setVendorName(r.getVendor().getShopName());
        }
        return d;
    }
}
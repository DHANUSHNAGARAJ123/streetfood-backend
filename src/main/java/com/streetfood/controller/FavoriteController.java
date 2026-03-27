package com.streetfood.controller;

import com.streetfood.dto.VendorDTO;
import com.streetfood.model.Favorite;
import com.streetfood.model.User;
import com.streetfood.model.Vendor;
import com.streetfood.repository.FavoriteRepository;
import com.streetfood.repository.UserRepository;
import com.streetfood.repository.VendorRepository;
import com.streetfood.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final VendorService vendorService;

    private User getUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // GET all favorites
    @GetMapping
    public ResponseEntity<List<VendorDTO>> getFavorites(Authentication auth) {
        try {
            User user = getUser(auth);
            List<VendorDTO> favs = favoriteRepository.findByCustomerId(user.getId())
                .stream()
                .map(f -> vendorService.toDTO(f.getVendor()))
                .collect(Collectors.toList());
            return ResponseEntity.ok(favs);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    // POST add favorite
    @PostMapping("/{vendorId}")
    public ResponseEntity<Map<String,Object>> addFavorite(
            @PathVariable Long vendorId,
            Authentication auth) {
        try {
            User user = getUser(auth);
            // Already favorited?
            if (favoriteRepository.findByCustomerIdAndVendorId(user.getId(), vendorId).isPresent()) {
                return ResponseEntity.ok(Map.of("message", "Already favorited", "favorited", true));
            }
            Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
            Favorite fav = new Favorite();
            fav.setCustomer(user);
            fav.setVendor(vendor);
            favoriteRepository.save(fav);
            return ResponseEntity.ok(Map.of("message", "Added to favorites", "favorited", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage(), "favorited", false));
        }
    }

    // DELETE remove favorite
    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Map<String,Object>> removeFavorite(
            @PathVariable Long vendorId,
            Authentication auth) {
        try {
            User user = getUser(auth);
            favoriteRepository.deleteByCustomerIdAndVendorId(user.getId(), vendorId);
            return ResponseEntity.ok(Map.of("message", "Removed from favorites", "favorited", false));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage(), "favorited", false));
        }
    }

    // GET check if favorited
    @GetMapping("/check/{vendorId}")
    public ResponseEntity<Map<String,Boolean>> checkFavorite(
            @PathVariable Long vendorId,
            Authentication auth) {
        try {
            User user = getUser(auth);
            boolean isFav = favoriteRepository.findByCustomerIdAndVendorId(user.getId(), vendorId).isPresent();
            return ResponseEntity.ok(Map.of("favorited", isFav));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("favorited", false));
        }
    }
}
package com.streetfood.service;
import com.streetfood.dto.VendorDTO;
import com.streetfood.model.Favorite;
import com.streetfood.model.User;
import com.streetfood.model.Vendor;
import com.streetfood.repository.FavoriteRepository;
import com.streetfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final VendorRepository vendorRepository;
    private final VendorService vendorService;

    public List<VendorDTO> getFavorites(User user) {
        return favoriteRepository.findByCustomerId(user.getId()).stream()
            .map(f -> vendorService.toDTO(f.getVendor())).collect(Collectors.toList());
    }

    public void addFavorite(User user, Long vendorId) {
        if (favoriteRepository.findByCustomerIdAndVendorId(user.getId(), vendorId).isPresent()) return;
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor not found"));
        Favorite fav = new Favorite();
        fav.setCustomer(user); fav.setVendor(vendor);
        favoriteRepository.save(fav);
    }

    public void removeFavorite(User user, Long vendorId) {
        favoriteRepository.findByCustomerIdAndVendorId(user.getId(), vendorId).ifPresent(favoriteRepository::delete);
    }

    public boolean isFavorited(User user, Long vendorId) {
        return favoriteRepository.findByCustomerIdAndVendorId(user.getId(), vendorId).isPresent();
    }
}
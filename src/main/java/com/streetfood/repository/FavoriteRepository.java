package com.streetfood.repository;

import com.streetfood.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByCustomerId(Long customerId);
    Optional<Favorite> findByCustomerIdAndVendorId(Long customerId, Long vendorId);
    void deleteByCustomerIdAndVendorId(Long customerId, Long vendorId);
}
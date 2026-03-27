package com.streetfood.service;

import com.streetfood.model.MenuItem;
import com.streetfood.model.Vendor;
import com.streetfood.repository.MenuItemRepository;
import com.streetfood.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final VendorRepository vendorRepository;

    public List<MenuItem> getMenuByVendorId(Long vendorId) {
        return menuItemRepository.findByVendorId(vendorId);
    }

    public List<MenuItem> getMenuByOwnerEmail(String email) {
        Vendor vendor = vendorRepository.findByOwnerEmail(email)
            .orElseGet(() -> vendorRepository.findAll().stream()
                .filter(v -> v.getUser() != null && email.equals(v.getUser().getEmail()))
                .findFirst().orElseThrow(() -> new RuntimeException("Vendor not found")));
        return menuItemRepository.findByVendorId(vendor.getId());
    }

    public MenuItem addItem(String email, MenuItem item) {
        Vendor vendor = vendorRepository.findByOwnerEmail(email)
            .orElseGet(() -> vendorRepository.findAll().stream()
                .filter(v -> v.getUser() != null && email.equals(v.getUser().getEmail()))
                .findFirst().orElseThrow(() -> new RuntimeException("Vendor not found")));
        item.setVendor(vendor);
        if (item.getIsAvailable() == null) item.setIsAvailable(true);
        return menuItemRepository.save(item);
    }

    public MenuItem updateItem(Long id, MenuItem updated) {
        MenuItem item = menuItemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
        if (updated.getName()        != null) item.setName(updated.getName());
        if (updated.getDescription() != null) item.setDescription(updated.getDescription());
        if (updated.getCategory()    != null) item.setCategory(updated.getCategory());
        if (updated.getPrice()       != null) item.setPrice(updated.getPrice());
        if (updated.getIsAvailable() != null) item.setIsAvailable(updated.getIsAvailable());
        return menuItemRepository.save(item);
    }

    public void deleteItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
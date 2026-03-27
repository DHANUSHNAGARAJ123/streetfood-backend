package com.streetfood.service;

import com.streetfood.dto.AuthDTOs;
import com.streetfood.model.AdminCredential;
import com.streetfood.model.User;
import com.streetfood.model.Vendor;
import com.streetfood.repository.AdminCredentialRepository;
import com.streetfood.repository.UserRepository;
import com.streetfood.repository.VendorRepository;
import com.streetfood.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final AdminCredentialRepository adminCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthDTOs.AuthResponse login(AuthDTOs.LoginRequest req) {
        String email = req.getEmail().trim();
        String password = req.getPassword();

        // Check admin_credentials table first
        var adminOpt = adminCredentialRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            AdminCredential admin = adminOpt.get();
            // Plain text password comparison (stored as plain in admin table)
            if (!password.equals(admin.getPassword())) {
                throw new RuntimeException("Invalid email or password");
            }
            // Generate token with ADMIN role
            String token = jwtUtil.generateToken(email, "ADMIN");
            return new AuthDTOs.AuthResponse(token, 0L, admin.getName(), email, "ADMIN", null);
        }

        // Normal users - check users table
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Block - admin email should not be in users table
        if ("ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthDTOs.AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getPhone());
    }

    public AuthDTOs.AuthResponse registerCustomer(AuthDTOs.RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail().trim()).isPresent())
            throw new RuntimeException("Email already registered");
        User user = new User();
        user.setName(req.getName().trim());
        user.setEmail(req.getEmail().trim());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("CUSTOMER");
        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole());
        return new AuthDTOs.AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(), saved.getPhone());
    }

    public AuthDTOs.AuthResponse registerVendor(AuthDTOs.RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail().trim()).isPresent())
            throw new RuntimeException("Email already registered");

        User user = new User();
        user.setName(req.getName().trim());
        user.setEmail(req.getEmail().trim());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("VENDOR");
        User saved = userRepository.save(user);

        String shopName = (req.getShopName() != null && !req.getShopName().trim().isEmpty())
            ? req.getShopName().trim()
            : req.getName().trim() + "'s Shop";

        Vendor vendor = new Vendor();
        vendor.setUser(saved);
        vendor.setOwnerName(saved.getName());
        vendor.setOwnerEmail(saved.getEmail());
        vendor.setOwnerPhone(saved.getPhone());
        vendor.setShopName(shopName);
        vendor.setStatus("PENDING");
        vendor.setIsLive(false);
        vendorRepository.save(vendor);

        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole());
        return new AuthDTOs.AuthResponse(token, saved.getId(), saved.getName(), saved.getEmail(), "VENDOR", saved.getPhone());
    }
}
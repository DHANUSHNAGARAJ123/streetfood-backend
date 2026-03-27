package com.streetfood.controller;

import com.streetfood.dto.AuthDTOs;
import com.streetfood.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTOs.LoginRequest req) {
        try {
            return ResponseEntity.ok(authService.login(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthDTOs.RegisterRequest req) {
        try {
            System.out.println("Register customer: " + req.getEmail());
            AuthDTOs.AuthResponse res = authService.registerCustomer(req);
            System.out.println("Customer registered, role: " + res.getRole());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println("Register customer error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody AuthDTOs.RegisterRequest req) {
        try {
            System.out.println("Register vendor: " + req.getEmail() + ", shop: " + req.getShopName());
            AuthDTOs.AuthResponse res = authService.registerVendor(req);
            System.out.println("Vendor registered, role: " + res.getRole());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println("Register vendor error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
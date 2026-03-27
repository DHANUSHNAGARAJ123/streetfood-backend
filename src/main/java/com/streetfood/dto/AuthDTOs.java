package com.streetfood.dto;

public class AuthDTOs {

    public static class LoginRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public void setEmail(String e) { this.email = e; }
        public String getPassword() { return password; }
        public void setPassword(String p) { this.password = p; }
    }

    public static class RegisterRequest {
        private String name;
        private String email;
        private String phone;
        private String password;
        private String shopName;
        public String getName() { return name; }
        public void setName(String n) { this.name = n; }
        public String getEmail() { return email; }
        public void setEmail(String e) { this.email = e; }
        public String getPhone() { return phone; }
        public void setPhone(String p) { this.phone = p; }
        public String getPassword() { return password; }
        public void setPassword(String p) { this.password = p; }
        public String getShopName() { return shopName; }
        public void setShopName(String s) { this.shopName = s; }
    }

    public static class AuthResponse {
        private String token;
        private Long id;
        private String name;
        private String email;
        private String role;
        private String phone;

        public AuthResponse() {}
        public AuthResponse(String token, Long id, String name, String email, String role, String phone) {
            this.token = token; this.id = id; this.name = name;
            this.email = email; this.role = role; this.phone = phone;
        }
        public String getToken() { return token; }
        public void setToken(String t) { this.token = t; }
        public Long getId() { return id; }
        public void setId(Long i) { this.id = i; }
        public String getName() { return name; }
        public void setName(String n) { this.name = n; }
        public String getEmail() { return email; }
        public void setEmail(String e) { this.email = e; }
        public String getRole() { return role; }
        public void setRole(String r) { this.role = r; }
        public String getPhone() { return phone; }
        public void setPhone(String p) { this.phone = p; }
    }
}
package com.streetfood.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    private final String uploadDir = "uploads/";

    @PostMapping("/vendor-photo")
    public ResponseEntity<?> uploadVendorPhoto(@RequestParam("file") MultipartFile file) {
        return saveFile(file, "vendors");
    }

    @PostMapping("/vendor-logo")
    public ResponseEntity<?> uploadVendorLogo(@RequestParam("file") MultipartFile file) {
        return saveFile(file, "logos");
    }

    private ResponseEntity<?> saveFile(MultipartFile file, String folder) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","No file"));

            // Validate type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/"))
                return ResponseEntity.badRequest().body(Map.of("message","Only images allowed"));

            // Max 5MB
            if (file.getSize() > 5 * 1024 * 1024)
                return ResponseEntity.badRequest().body(Map.of("message","File too large. Max 5MB"));

            // Create directory
            String dirPath = uploadDir + folder;
            File dir = new File(dirPath);
            if (!dir.exists()) dir.mkdirs();

            // Unique filename
            String ext = getExt(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + ext;
            Path filePath = Paths.get(dirPath, filename);
            Files.write(filePath, file.getBytes());

            // Return URL
            String url = "http://localhost:8080/uploads/" + folder + "/" + filename;
            return ResponseEntity.ok(Map.of("url", url, "filename", filename));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("message","Upload failed: " + e.getMessage()));
        }
    }

    private String getExt(String filename) {
        if (filename == null) return ".jpg";
        int dot = filename.lastIndexOf(".");
        return dot > 0 ? filename.substring(dot) : ".jpg";
    }
}
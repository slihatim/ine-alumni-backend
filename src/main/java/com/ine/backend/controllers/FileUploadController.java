package com.ine.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

	private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

	@PostMapping("/upload")
	// @PreAuthorize("hasAuthority('resource:create')")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		// Create upload directory if it doesn't exist
		File uploadDir = new File(UPLOAD_DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// Clean and validate filename - handle null case
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || originalFilename.trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid filename: filename cannot be null or empty");
		}

		String filename = StringUtils.cleanPath(originalFilename);
		if (filename.contains("..")) {
			return ResponseEntity.badRequest().body("Invalid filename: path traversal attempt detected");
		}

		// Validate file type (optional security measure)
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("File cannot be empty");
		}

		// Save file
		try {
			Path filePath = Paths.get(UPLOAD_DIR, filename);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			String fileUrl = "/uploads/" + filename;
			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			throw new RuntimeException("Error occurred during file upload: " + e.getMessage(), e);
		}
	}
}

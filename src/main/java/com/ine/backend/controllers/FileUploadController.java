package com.ine.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FileUploadController {

	private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			File uploadDir = new File(UPLOAD_DIR);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			String filename = StringUtils.cleanPath(file.getOriginalFilename());
			Path filePath = Paths.get(UPLOAD_DIR, filename);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			String fileUrl = "/uploads/" + filename;

			return ResponseEntity.ok(fileUrl);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload");
		}
	}
}

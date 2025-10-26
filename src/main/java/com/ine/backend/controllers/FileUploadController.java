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

import com.ine.backend.dto.ApiResponseDto;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploadController {

	private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

	@PostMapping("/upload")
	// @PreAuthorize("hasAuthority('resource:create')")
	public ResponseEntity<ApiResponseDto<String>> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			// Create upload directory if it doesn't exist
			File uploadDir = new File(UPLOAD_DIR);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			// Clean and validate filename - handle null case
			String originalFilename = file.getOriginalFilename();
			if (originalFilename == null || originalFilename.trim().isEmpty()) {
				ApiResponseDto<String> response = ApiResponseDto.<String>builder()
						.message("Invalid filename: filename cannot be null or empty").response(null).isSuccess(false)
						.build();
				return ResponseEntity.badRequest().body(response);
			}

			String filename = StringUtils.cleanPath(originalFilename);
			if (filename.contains("..")) {
				ApiResponseDto<String> response = ApiResponseDto.<String>builder()
						.message("Invalid filename: path traversal attempt detected").response(null).isSuccess(false)
						.build();
				return ResponseEntity.badRequest().body(response);
			}

			// Validate file type (optional security measure)
			if (file.isEmpty()) {
				ApiResponseDto<String> response = ApiResponseDto.<String>builder().message("File cannot be empty")
						.response(null).isSuccess(false).build();
				return ResponseEntity.badRequest().body(response);
			}

			// Save file
			Path filePath = Paths.get(UPLOAD_DIR, filename);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			String fileUrl = "/uploads/" + filename;

			ApiResponseDto<String> response = ApiResponseDto.<String>builder().message("File uploaded successfully")
					.response(fileUrl).isSuccess(true).build();

			return ResponseEntity.ok(response);

		} catch (IOException e) {
			ApiResponseDto<String> response = ApiResponseDto.<String>builder()
					.message("Error occurred during file upload: " + e.getMessage()).response(null).isSuccess(false)
					.build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}

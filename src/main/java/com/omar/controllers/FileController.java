package com.omar.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.omar.errors.FileStorageException;
import com.omar.services.StorageService;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	StorageService fileServcie;

	@PostMapping
	public String uploadFile(@RequestParam MultipartFile file) {

		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		System.out.println(file);

		try {
			String filename = fileServcie.store(file);
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("");
		}
	}

	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE, path = "/{fileName}")
	public Resource getFile(@PathVariable String fileName) throws IOException {
		return fileServcie.loadAsResource(fileName); // .getInputStream();

		// return
		// IOUtils.toByteArray(fileServcie.loadAsResource(fileName).getInputStream());
	}
}

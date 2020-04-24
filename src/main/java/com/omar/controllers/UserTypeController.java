package com.omar.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usertype")
public class UserTypeController {

	@GetMapping
	public ResponseEntity<?> get() {

		return ResponseEntity.ok("");
	}

}

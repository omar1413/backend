package com.omar.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omar.entities.PasswordResetToken;
import com.omar.entities.UserEntity;
import com.omar.errors.ErrorResponse;
import com.omar.services.EmailService;
import com.omar.services.PasswordResetTokenService;
import com.omar.services.UserService;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetTokenController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@Autowired
	private EmailService emailService;

	@PostMapping("/send-token")
	public ResponseEntity<?> getResetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		UserEntity user = userService.findByEmail(userEmail);
		if (user == null) {
			return new ResponseEntity<>(new ErrorResponse("No user found"), HttpStatus.NOT_FOUND);
		}
		String token = RandomStringUtils.randomNumeric(4);
		passwordResetTokenService.createPasswordResetTokenForUser(user, token);
		emailService.sendEmail(userEmail, "password reset", token);
		return null;
	}

	@PostMapping(path = "/{token}")
	public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam String password,
			@PathVariable String token) {
		PasswordResetToken passwordResetToken = passwordResetTokenService.getTokenReset(token);
		if (passwordResetToken == null) {
			return new ResponseEntity<>(new ErrorResponse("invalid token"), HttpStatus.NOT_FOUND);
		}
		UserEntity user = passwordResetToken.getUser();

		user.setPassword(password);
		return new ResponseEntity<>(userService.update(user), HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> checkToken(@RequestParam String token) {
		PasswordResetToken passwordResetToken = passwordResetTokenService.getTokenReset(token);
		if (passwordResetToken != null) {
			return new ResponseEntity<>(new GenericResponse("/password-reset/" + passwordResetToken.getToken()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

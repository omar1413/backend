package com.omar.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omar.entities.SellerEntity;
import com.omar.errors.AuthorizationException;
import com.omar.errors.ErrorResponse;
import com.omar.errors.ImageUploadException;
import com.omar.errors.UserExistException;
import com.omar.security.SecurityConstants;
import com.omar.services.SellerService;
import com.omar.utils.Utils;

@RestController
@RequestMapping("/sellers")
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Valid @RequestBody SellerEntity user, HttpServletRequest req,
			HttpServletResponse res) {

		try {
			SellerEntity result = sellerService.createUser(user);
			String token = Utils.createToken(user.getUsername());
			res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (UserExistException e) {
			ErrorResponse error = new ErrorResponse("user already exist");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} catch (ImageUploadException e) {
			ErrorResponse error = new ErrorResponse("error in upload image");

			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse("unknown error");
			e.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}

	}

	private SellerEntity getAuthorizedUser(HttpServletRequest req) {
		String username;
		try {
			username = Utils.getAuthorizedUser(req);
		} catch (AuthorizationException e) {

			e.printStackTrace();
			return null;
		}

		SellerEntity user = sellerService.get(username);

		return user;
	}

	@GetMapping
	public ResponseEntity<?> user(HttpServletRequest req) {

		SellerEntity user = getAuthorizedUser(req);
		if (user == null) {
			return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> sellerUser(HttpServletRequest req) {

		SellerEntity user = getAuthorizedUser(req);
		if (user == null) {
			return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(user.getUsers(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@Valid @RequestBody SellerEntity user, HttpServletRequest req) {
		SellerEntity authedUser = getAuthorizedUser(req);
		if (authedUser == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (authedUser.getId() == user.getId()) {
			System.out.println("ay 7aga");
			return new ResponseEntity<>(sellerService.update(user), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}

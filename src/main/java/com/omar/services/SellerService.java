package com.omar.services;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omar.entities.SellerEntity;
import com.omar.entities.UserEntity;
import com.omar.errors.ImageUploadException;
import com.omar.errors.UserExistException;
import com.omar.repositories.PasswordTokenRepository;
import com.omar.repositories.SellerRepository;
import com.omar.repositories.UserRepository;

@Service
public class SellerService {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	public SellerEntity createUser(SellerEntity user) throws UserExistException, ImageUploadException {

		SellerEntity sellerResult = sellerRepository.findByUsername(user.getUsername());
		SellerEntity sellerEmailResult = sellerRepository.findByEmail(user.getEmail());

		UserEntity userResult = userRepository.findByUsername(user.getUsername());
		UserEntity userEmailResult = userRepository.findByEmail(user.getEmail());

		// System.out.println(result);
		if (sellerResult == null && sellerEmailResult == null && userResult == null && userEmailResult == null) {

			// String path = saveImage(user.getProfileImage());
			// user.setProfileImage(path);
			if (user.getProfileImage() == null || user.getProfileImage().isEmpty()) {
				user.setProfileImage("place_holder.jpg");
			}
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			sellerRepository.save(user);
			return user;

		}

		throw new UserExistException();
	}

	public SellerEntity update(SellerEntity user) {

//		String path = updateImage();
//		user.setProfileImage(path);
		SellerEntity oldUser = getById(user.getId());
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			user.setPassword(oldUser.getPassword());
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		return sellerRepository.save(user);

	}

	public SellerEntity get(String username) {
		return sellerRepository.findByUsername(username);
	}

	public SellerEntity getById(Long id) {
		return sellerRepository.findById(id).get();
	}

	public SellerEntity findByEmail(String userEmail) {

		return sellerRepository.findByEmail(userEmail);
	}
}

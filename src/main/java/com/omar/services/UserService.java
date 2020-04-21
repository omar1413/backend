package com.omar.services;

import java.util.NoSuchElementException;

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
public class UserService {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	public UserEntity createUser(UserEntity user, String id) throws UserExistException, ImageUploadException {

		long sellerId = -1;
		try {
			sellerId = Long.parseLong(id);
		} catch (Exception e) {
			throw new NoSuchElementException();
		}

		SellerEntity sellerResult = sellerRepository.findByUsername(user.getUsername());
		SellerEntity sellerEmailResult = sellerRepository.findByEmail(user.getEmail());

		UserEntity userResult = userRepository.findByUsername(user.getUsername());
		UserEntity userEmailResult = userRepository.findByEmail(user.getEmail());

		// System.out.println(result);
		if (userResult == null && userEmailResult == null && sellerResult == null && sellerEmailResult == null) {

			SellerEntity seller = sellerRepository.findById(sellerId).get();
			if (seller == null) {
				throw new UserExistException();
			}

			user.setSeller(seller);
			// String path = saveImage(user.getProfileImage());
			// user.setProfileImage(path);
			if (user.getProfileImage() == null || user.getProfileImage().isEmpty()) {
				user.setProfileImage("place_holder.jpg");
			}
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return user;

		}

		throw new UserExistException();
	}

	public UserEntity update(UserEntity user) {

//		String path = updateImage();
//		user.setProfileImage(path);
		UserEntity oldUser = getById(user.getId());
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			user.setPassword(oldUser.getPassword());
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		return userRepository.save(user);

	}

//	private String updateImage() {
//
//		return null;
//	}
//
//	private String saveImage(String base64) throws ImageUploadException {
//		if (base64 != null && !base64.isEmpty()) {
//
//			FileOutputStream fos = null;
//			try {
//				// This will decode the String which is encoded by using Base64 class
//				// System.out.println(base64);
//				// byte[] imageByte = Base64.getDecoder().decode(base64);
//				byte[] imageByte = Base64.decodeBase64(base64);
//				// byte[] imageByte = decoded.getBytes();
//				String directory = "D:\\SERVER\\" + "images\\sample1.jpg";
//
//				fos = new FileOutputStream(directory);
//				fos.write(imageByte);
//
//				return directory;
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new ImageUploadException();
//			} finally {
//				if (fos != null) {
//					try {
//						fos.close();
//					} catch (IOException e) {
//
//						e.printStackTrace();
//						throw new ImageUploadException();
//
//					}
//				}
//
//			}
//
//		}
//		return "";
//	}

	public UserEntity get(String username) {
		return userRepository.findByUsername(username);
	}

	public UserEntity getById(Long id) {
		return userRepository.findById(id).get();
	}

	public UserEntity findByEmail(String userEmail) {

		return userRepository.findByEmail(userEmail);
	}

}

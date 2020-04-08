package com.omar.services;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omar.entities.PasswordResetToken;
import com.omar.entities.UserEntity;
import com.omar.errors.ImageUploadException;
import com.omar.errors.UserExistException;
import com.omar.repositories.PasswordTokenRepository;
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
	private PasswordTokenRepository passwordTokenRepository;

	public UserEntity createUser(UserEntity user) throws UserExistException, ImageUploadException {

		UserEntity result = userRepository.findByUsername(user.getUsername());

		System.out.println(result);
		if (result == null) {
			// String path = saveImage(user.getProfileImage());
			// user.setProfileImage(path);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return user;

		}

		throw new UserExistException();
	}

	public UserEntity update(UserEntity user) {

		String path = updateImage();
		user.setProfileImage(path);

		return userRepository.save(user);

	}

	private String updateImage() {

		return null;
	}

	private String saveImage(String base64) throws ImageUploadException {
		if (base64 != null && !base64.isEmpty()) {

			FileOutputStream fos = null;
			try {
				// This will decode the String which is encoded by using Base64 class
				// System.out.println(base64);
				// byte[] imageByte = Base64.getDecoder().decode(base64);
				byte[] imageByte = Base64.decodeBase64(base64);
				// byte[] imageByte = decoded.getBytes();
				String directory = "D:\\SERVER\\" + "images\\sample1.jpg";

				fos = new FileOutputStream(directory);
				fos.write(imageByte);

				return directory;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ImageUploadException();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {

						e.printStackTrace();
						throw new ImageUploadException();

					}
				}

			}

		}
		return "";
	}

	public void createPasswordResetTokenForUser(UserEntity user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	public PasswordResetToken getTokenReset(String token) {

		return passwordTokenRepository.findByToken(token);
	}

	public UserEntity get(String username) {
		return userRepository.findByUsername(username);
	}

	public UserEntity findByEmail(String userEmail) {

		return userRepository.findByEmail(userEmail);
	}

}

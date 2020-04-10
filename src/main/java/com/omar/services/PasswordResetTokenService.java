package com.omar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omar.entities.PasswordResetToken;
import com.omar.entities.UserEntity;
import com.omar.repositories.PasswordTokenRepository;

@Service
public class PasswordResetTokenService {

	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	public void createPasswordResetTokenForUser(UserEntity user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	public PasswordResetToken getTokenReset(String token) {

		return passwordTokenRepository.findByToken(token);
	}

	public PasswordResetToken getTokenResetById(long id) {

		return passwordTokenRepository.findById(id).get();
	}

}

package com.omar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omar.entities.SellerEntity;
import com.omar.entities.UserEntity;
import com.omar.repositories.SellerRepository;
import com.omar.repositories.UserRepository;

@Service
public class UserTypeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SellerRepository sellerRepository;

	public int loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);

		SellerEntity seller = sellerRepository.findByUsername(username);
		if (user == null && seller == null) {
			throw new UsernameNotFoundException(username);
		}

		int userType = -1;
		if (user == null) {
			userType = seller.getUserType();
		} else {
			userType = user.getUserType();
		}
		return userType;
	}
}

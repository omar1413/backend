package com.omar.services;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omar.entities.SellerEntity;
import com.omar.entities.UserEntity;
import com.omar.repositories.SellerRepository;
import com.omar.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserRepository userRepository;

	private SellerRepository sellerRepository;

	public UserDetailsServiceImpl(UserRepository userRepository, SellerRepository sellerRepository) {
		this.userRepository = userRepository;
		this.sellerRepository = sellerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);

		SellerEntity seller = sellerRepository.findByUsername(username);
		if (user == null && seller == null) {
			throw new UsernameNotFoundException(username);
		}

		String usernameRes = null;
		String passwordRes = null;
		if (user == null) {
			usernameRes = seller.getUsername();
			passwordRes = seller.getPassword();
		} else {
			usernameRes = user.getUsername();
			passwordRes = user.getPassword();
		}
		return new User(usernameRes, passwordRes, emptyList());
	}
}

package com.omar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omar.entities.PasswordResetToken;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	public PasswordResetToken findByToken(String token);
}

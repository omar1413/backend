package com.omar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omar.entities.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
	public SellerEntity findByUsername(String username);

	public SellerEntity findByEmail(String email);
}

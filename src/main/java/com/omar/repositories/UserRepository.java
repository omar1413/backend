package com.omar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omar.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByUsername(String username);

	public UserEntity findByEmail(String email);

}

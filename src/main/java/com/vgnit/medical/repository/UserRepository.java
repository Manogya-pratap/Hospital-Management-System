package com.vgnit.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vgnit.medical.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
	// For Login !
	User findByEmail(String email);

	public boolean existsByEmail(String email);

}
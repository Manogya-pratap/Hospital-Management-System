package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Phar;

public interface PharmaRepository extends JpaRepository<Phar, Long>
{
	@Query("SELECT phar FROM Phar phar WHERE phar.gender LIKE %?1%"
			+ " OR phar.firstName LIKE %?1%"
			+ " OR phar.lastName LIKE %?1%"
			+ " OR phar.mobile LIKE %?1%")
	List<Phar> findKeyword(String keyword);
}
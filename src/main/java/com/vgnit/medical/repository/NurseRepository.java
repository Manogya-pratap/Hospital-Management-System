package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Nur;

public interface NurseRepository extends JpaRepository<Nur, Long>
{
	@Query("SELECT nur FROM Nur nur WHERE nur.email LIKE %?1%"
			+ " OR nur.firstName LIKE %?1%"
			+ " OR nur.lastName LIKE %?1%"
			+ " OR nur.mobile LIKE %?1%")
	List<Nur> findKeyword(String keyword);

	@Query("SELECT we FROM Nur we WHERE we.status LIKE 'active%'")
	List<Nur> to();

}
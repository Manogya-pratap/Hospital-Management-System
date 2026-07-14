package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>
{

	@Query("SELECT p FROM Patient p WHERE p.firstName LIKE %?1%"
			+ " OR p.lastName LIKE %?1%"
			+ " OR p.mobile LIKE %?1%")
	List<Patient> search(String keyword);

	@Query("SELECT q FROM Patient q WHERE q.status LIKE 'active%'")
	List<Patient> fp();

}
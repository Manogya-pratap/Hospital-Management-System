package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.AddDoctor;

public interface DoctorRepository extends JpaRepository<AddDoctor, Long>
{

	// For Add Doctor !
	public boolean existsByEmail(String email);

	@Query("SELECT doctor FROM AddDoctor doctor WHERE doctor.gender LIKE %?1%"
			+ " OR doctor.firstName LIKE %?1%"
			+ " OR doctor.lastName LIKE %?1%"
			+ " OR doctor.department LIKE %?1%")
	public List<AddDoctor> search(String keyword);

	@Query("SELECT u FROM AddDoctor u WHERE u.status LIKE 'active%'")
	public List<AddDoctor> findStatus();

}
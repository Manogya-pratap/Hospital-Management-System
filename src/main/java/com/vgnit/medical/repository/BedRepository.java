package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Bed;

public interface BedRepository extends JpaRepository<Bed, Long>
{
	@Query("SELECT kl FROM Bed kl WHERE kl.bedType LIKE %?1%"
			+ " OR kl.bedCap LIKE %?1%"
			+ " OR kl.bedDesc LIKE %?1%")
	List<Bed> findKeyword(String keyword);

}
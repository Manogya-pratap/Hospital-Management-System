package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{

	@Query("SELECT DP FROM Appointment DP WHERE DP.patient LIKE %?1%"
			+ " OR DP.depart LIKE %?1%"
			+ " OR DP.doctorn LIKE %?1%")
	List<Appointment> findKeyword(String keyword);

}
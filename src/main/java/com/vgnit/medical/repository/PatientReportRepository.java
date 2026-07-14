package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.PatientReport;

public interface PatientReportRepository extends JpaRepository<PatientReport, Long>
{
	@Query("SELECT patientReport FROM PatientReport patientReport WHERE patientReport.patientName LIKE %?1%"
			+ " OR patientReport.description LIKE %?1%"
			+ " OR patientReport.assignDate LIKE %?1%")
	List<PatientReport> getThisss(String keyword);
}
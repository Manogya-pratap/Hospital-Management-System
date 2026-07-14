package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>
{

	@Query("SELECT department from Department department WHERE department.deptName LIKE %?1%"
			+ " OR department.description LIKE %?1%"
			+ " OR department.status LIKE %?1%")
	List<Department> findthis(String keyword);

}
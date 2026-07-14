package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Long>
{
	@Query("SELECT emp FROM Emp emp WHERE emp.typeEmp LIKE %?1%"
			+ " OR emp.firstName LIKE %?1%"
			+ " OR emp.lastName LIKE %?1%"
			+ " OR emp.mobile LIKE %?1%")
	List<Emp> findKeyword(String keyword);

}
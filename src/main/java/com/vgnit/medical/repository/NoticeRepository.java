package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgnit.medical.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>
{
	@Query("SELECT raj FROM Notice raj WHERE raj.title LIKE %?1%"
			+ " OR raj.bedDesc LIKE %?1%"
			+ " OR raj.startDate LIKE %?1%")
	List<Notice> findKeyword(String keyword);
}
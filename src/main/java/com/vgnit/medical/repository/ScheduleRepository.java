package com.vgnit.medical.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.vgnit.medical.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{
	@Query("SELECT PT FROM Schedule PT WHERE PT.dname LIKE %?1%"
			+ " OR PT.status LIKE %?1%"
			+ " OR PT.visibility LIKE %?1%")
	List<Schedule> findKeyword(String keyword);

}
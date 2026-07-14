package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.Schedule;

public interface ScheduleService 
{
	Schedule saveShedule(Schedule schedule);
	
	List<Schedule> getAllSchedule(String keyword);
	
	void deleteSchedule(Long id);
	
	Schedule getParticularSchedule(Long id);
	
}

package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.Schedule;
import com.vgnit.medical.repository.ScheduleRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class ScheduleServiceImpl implements ScheduleService
{
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	public Schedule saveShedule(Schedule schedule)
	{
		Schedule save = scheduleRepository.save(schedule);
		return save;
	}
	
	// (*)
	public void removeSessionMessage()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg"); 
	}
	
	// (*)
	public void removeSessionMessage2()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		session.removeAttribute("msgd"); 
	}
	
	// (*)
	public void removeSessionMessage3()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		session.removeAttribute("msgu"); 
	}

	@Override
	public List<Schedule> getAllSchedule(String keyword)
	{
		if(keyword!=null)
		{
			return scheduleRepository.findKeyword(keyword);
		}
		else
		{
			return scheduleRepository.findAll();
		}
	}

	@Override
	public void deleteSchedule(Long id) 
	{
		scheduleRepository.deleteById(id);
		
	}

	@Override
	public Schedule getParticularSchedule(Long id)
	{
		Optional<Schedule> findById = scheduleRepository.findById(id);
		Schedule schedule = findById.get();
		return schedule;
	}

}

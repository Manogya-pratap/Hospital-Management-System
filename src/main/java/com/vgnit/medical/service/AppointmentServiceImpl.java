package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vgnit.medical.entity.Appointment;
import com.vgnit.medical.repository.AppointmentRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class AppointmentServiceImpl implements AppointmentService
{
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public Appointment saveAppointment(Appointment appointment)
	{
		Appointment save = appointmentRepository.save(appointment);
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
	public List<Appointment> getAllAppointment(String keyword)
	{
		if(keyword!=null)
		{
			return appointmentRepository.findKeyword(keyword);
		}
		else
		{
			return appointmentRepository.findAll();
		}
	}

	@Override
	public void deleteAppointment(Long id) 
	{
		appointmentRepository.deleteById(id);
		
	}

	@Override
	public Appointment getParticularAppointment(Long id)
	{
		Optional<Appointment> findById = appointmentRepository.findById(id);
		Appointment appointment = findById.get();
		return appointment;
	}

}

package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.AddDoctor;
import com.vgnit.medical.repository.DoctorRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class DoctorServiceImpl implements DoctorService
{
	@Override
	public List<AddDoctor> findst()
	{
		List<AddDoctor> status = doctorRepository.findStatus();
		return status;
	} 
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public boolean checkEmail(String email) 
	{
		return doctorRepository.existsByEmail(email);
	}

	@Override
	public AddDoctor addDoctor(AddDoctor addDoctor)
	{
		AddDoctor saveDoctor = doctorRepository.save(addDoctor);
		return saveDoctor;
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
	public List<AddDoctor> listAll(String keyword)
	{
		if(keyword != null)
		{
			return doctorRepository.search(keyword);
		}
		else
		{
			return(List<AddDoctor>) doctorRepository.findAll();
		}	
	}

	@Override
	public void deletDoctor(Long id)
	{
		doctorRepository.deleteById(id);
	}

	@Override
	public AddDoctor getDoctorById(Long id) 
	{
		Optional<AddDoctor> findById = doctorRepository.findById(id);
		AddDoctor addDoctor = findById.get();
		return addDoctor;
	}
	

}

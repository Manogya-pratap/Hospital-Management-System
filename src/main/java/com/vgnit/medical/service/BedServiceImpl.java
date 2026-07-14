package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.Bed;
import com.vgnit.medical.repository.BedRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class BedServiceImpl implements BedService
{
	@Autowired
	private BedRepository bedRepository;
	
	@Override
	public List<Bed> bpl()
	{
		return bedRepository.findAll();
	}
	
	@Override
	public Bed saveBed(Bed bed)
	{
		Bed save = bedRepository.save(bed);
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
	public List<Bed> getAllBed(String keyword)
	{
		if(keyword!=null)
		{
			return bedRepository.findKeyword(keyword);
		}
		else
		{
			return bedRepository.findAll();
		}
	}

	@Override
	public void deleteBed(Long id) 
	{
		bedRepository.deleteById(id);
		
	}

	@Override
	public Bed getParticularBed(Long id)
	{
		Optional<Bed> findById = bedRepository.findById(id);
		Bed bed = findById.get();
		return bed;
	}

}

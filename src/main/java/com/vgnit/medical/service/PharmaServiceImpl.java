package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vgnit.medical.entity.Phar;
import com.vgnit.medical.repository.PharmaRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class PharmaServiceImpl implements PharmaService
{
	@Autowired
	private PharmaRepository pharRepository;
	
	@Override
	public List<Phar> lop()
	{
		return pharRepository.findAll();
	}
	
	@Override
	public Phar savePhar(Phar phar)
	{
		Phar add= pharRepository.save(phar);
		return add;
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
	public List<Phar> getAllPhar(String keyword)
	{
		if(keyword!=null)
		{
			return pharRepository.findKeyword(keyword);
		}
		else
		{
			return pharRepository.findAll();
		}
	}

	@Override
	public void deletePhar(Long id)
	{
		pharRepository.deleteById(id);
		
	}

	@Override
	public Phar getParticularPhar(Long id)
	{
		Optional<Phar> findId= pharRepository.findById(id);
		Phar phar = findId.get();
		return phar;
	}

}

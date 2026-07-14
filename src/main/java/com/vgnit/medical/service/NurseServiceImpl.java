package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.Nur;
import com.vgnit.medical.repository.NurseRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class NurseServiceImpl implements NurseService
{
	@Autowired
	private NurseRepository nurRepository;
	
	@Override
	public List<Nur> mm()
	{
		return nurRepository.to();
	}
	
	@Override
	public Nur saveNur(Nur nur)
	{
		Nur add= nurRepository.save(nur);
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
	public List<Nur> getAllNur(String keyword)
	{
		if(keyword!=null)
		{
			return nurRepository.findKeyword(keyword);
		}
		else
		{
			return nurRepository.findAll();
		}
	}

	@Override
	public void deleteNur(Long id)
	{
		nurRepository.deleteById(id);
		
	}

	@Override
	public Nur getParticularNur(Long id)
	{
		Optional<Nur> findId= nurRepository.findById(id);
		Nur nur = findId.get();
		return nur;
	}
	
}

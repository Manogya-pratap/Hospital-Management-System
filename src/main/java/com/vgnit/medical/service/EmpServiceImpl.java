package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vgnit.medical.entity.Emp;
import com.vgnit.medical.repository.EmpRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EmpServiceImpl implements EmpService
{
	@Autowired
	private EmpRepository empRepository;
	
	@Override
	public Emp saveEmp(Emp emp)
	{
		Emp add= empRepository.save(emp);
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
	public List<Emp> getAllEmp(String keyword)
	{
		if(keyword!=null)
		{
			return empRepository.findKeyword(keyword);
		}
		else
		{
			return empRepository.findAll();
		}
	}

	@Override
	public void deleteEmp(Long id)
	{
		empRepository.deleteById(id);
		
	}

	@Override
	public Emp getParticularEmp(Long id)
	{
		Optional<Emp> findId= empRepository.findById(id);
		Emp emp = findId.get();
		return emp;
	}
}

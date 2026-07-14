package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.vgnit.medical.entity.Department;
import com.vgnit.medical.repository.DepartmentRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class DepartmentService
{
	@Autowired 
	private DepartmentRepository departmentRepository;
	
	public long aql()
	{
		return departmentRepository.count();
	}
	
	public Department adddepartment(Department dept)
	{
		return departmentRepository.save(dept);
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

	public List<Department> getAllDept(String keyword) 
	{
		if(keyword!=null)
		{
		return departmentRepository.findthis(keyword);
		}
		else
		{
			return departmentRepository.findAll();
		}
	}

	public void deleteDept(Long id) 
	{
		departmentRepository.deleteById(id);
	}

	public Department getParticularDept(Long id)
	{
		Optional<Department> findById = departmentRepository.findById(id);
		return findById.get();
	}

}

package com.vgnit.medical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vgnit.medical.entity.Notice;
import com.vgnit.medical.repository.NoticeRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class NoticeServiceImpl implements NoticeService
{
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Override
	public Notice saveNotice(Notice notice)
	{
		Notice add= noticeRepository.save(notice);
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
	public List<Notice> getAllNotice(String keyword)
	{
		if(keyword!=null)
		{
			return noticeRepository.findKeyword(keyword);
		}
		else
		{
			return noticeRepository.findAll();
		}
	}

	@Override
	public void deleteNotice(Long id)
	{
		noticeRepository.deleteById(id);
		
	}

	@Override
	public Notice getParticularNotice(Long id)
	{
		Optional<Notice> findId= noticeRepository.findById(id);
		Notice notice = findId.get();
		return notice;
	}
}

package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.Notice;

public interface NoticeService 
{
	Notice saveNotice(Notice notice);

	List<Notice>getAllNotice(String keyword);

	void deleteNotice(Long id);

	Notice getParticularNotice(Long id);
}

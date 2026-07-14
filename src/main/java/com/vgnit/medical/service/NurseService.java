package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.Nur;

public interface NurseService 
{
	Nur saveNur(Nur nur);

	List<Nur>getAllNur(String keyword);

	void deleteNur(Long id);

	Nur getParticularNur(Long id);

	List<Nur> mm();
}

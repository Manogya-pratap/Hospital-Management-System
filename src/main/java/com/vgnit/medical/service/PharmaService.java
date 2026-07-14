package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.entity.Phar;

public interface PharmaService 
{
	Phar savePhar(Phar phar);

	List<Phar>getAllPhar(String keyword);

	void deletePhar(Long id);

	Phar getParticularPhar(Long id);

	List<Phar> lop();
}

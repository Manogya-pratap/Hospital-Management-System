package com.vgnit.medical.service;

import java.util.List;
import com.vgnit.medical.entity.Bed;

public interface BedService
{
	Bed saveBed(Bed bed);

	List<Bed>getAllBed(String keyword);

	void deleteBed(Long id);

	Bed getParticularBed(Long id);

	List<Bed> bpl();
}

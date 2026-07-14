package com.vgnit.medical.service;
import java.util.List;
import com.vgnit.medical.entity.Appointment;

public interface AppointmentService
{
	Appointment saveAppointment(Appointment appointment);

	List<Appointment> getAllAppointment(String keyword);

	void deleteAppointment(Long id);

	Appointment getParticularAppointment(Long id);

}

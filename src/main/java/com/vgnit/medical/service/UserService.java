package com.vgnit.medical.service;

import java.util.List;

import com.vgnit.medical.dto.UserDto;
import com.vgnit.medical.entity.User;

public interface UserService 
{
	User save(UserDto userDto);
	
	boolean checkEmail(String email);
	
	List<User> findAllUsers();

	User getByEmail(String email);
	
	User updateAccount(User user);

}

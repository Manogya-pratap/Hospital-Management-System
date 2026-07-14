package com.vgnit.medical.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vgnit.medical.dto.UserDto;
import com.vgnit.medical.entity.User;
import com.vgnit.medical.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User save(UserDto userDto)
	{
		User user= new User(
				userDto.getFullname(),
				userDto.getEmail(),
				passwordEncoder.encode(userDto.getPassword()),
				userDto.getRole());
		
		User save = userRepository.save(user);
		return save;
	}

	@Override
	public boolean checkEmail(String email) 
	{
		boolean existsByEmail = userRepository.existsByEmail(email);
		return existsByEmail;
	}
	
	// (*)
	public void removeSessionMessage()
	{
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		
		session.removeAttribute("msg"); 
	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User getByEmail(String email)
	{
		User findByEmail = userRepository.findByEmail(email);
		return findByEmail;
	}
	
	@Override
	public User updateAccount(User user)
	{
		User save = userRepository.save(user);
		return save;
	}

}


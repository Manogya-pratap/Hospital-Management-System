package com.vgnit.medical.service;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vgnit.medical.entity.User;

public class CustomUserDetails implements UserDetails
{
	private User user;
	
	public CustomUserDetails(User user) 
	{
		super();
		this.user = user;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// TODO Auto-generated method stub
		return List.of(()-> user.getRole());
	}
	
	public String getFullname()
	{
		// Own Creation !!
		return user.getFullname();
	}
	
	public void setFullname(String fullname)
	{
		// Own Creation !!
		this.user.setFullname(fullname);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	
	public void setUsername(String username)
	{
		// Own Creation !!
		this.user.setEmail(username);
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

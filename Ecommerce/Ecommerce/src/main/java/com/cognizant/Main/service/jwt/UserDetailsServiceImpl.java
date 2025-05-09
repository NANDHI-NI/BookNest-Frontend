package com.cognizant.Main.service.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.Main.entities.User;
import com.cognizant.Main.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByEmail(username);
		if(user==null) throw new UsernameNotFoundException("Username not found",null);
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getpassword(), new ArrayList<>());
	}

}

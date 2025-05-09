package com.cognizant.Main.controller;

import java.io.IOException;

import org.apache.coyote.BadRequestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.Main.dto.AuthenticationRequest;
//import com.cognizant.Main.dto.AuthenticationResponse;
import com.cognizant.Main.entities.User;
import com.cognizant.Main.repository.UserRepository;
//import com.cognizant.Main.service.user.UserService;
import com.cognizant.Main.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

//	@Autowired
//	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	                                                                                                                                                                                                                                  //servetException
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws BadCredentialsException,DisabledException,UsernameNotFoundException,IOException,JSONException,BadRequestException
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e)
		{
			throw new BadCredentialsException("Incorrect username or password");
		}
		catch(DisabledException disabledException)
		{
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"User is not activated");
			return;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());//it can be to different packages also 
		final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
//		return new AuthenticationResponse(jwt);
		
		JSONObject jsonObject = new JSONObject()
				.put("userId", user.getId())
				.put("role", user.getUserRole());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGGOTHER,Origin,X-Requested-With,Context-Type,Accept,X-Customheader");
		response.addHeader(HEADER_STRING, TOKEN_PREFIX+" "+jwt);
	}
}

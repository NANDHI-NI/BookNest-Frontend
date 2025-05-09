package com.cognizant.Main.dto;

public class AuthenticationResponse {
 
     //changed myself is error should check this by default it doesnot assign the jwt 
	public AuthenticationResponse(String jwt) {
		this.jwtToken=jwt;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	private String jwtToken;
}

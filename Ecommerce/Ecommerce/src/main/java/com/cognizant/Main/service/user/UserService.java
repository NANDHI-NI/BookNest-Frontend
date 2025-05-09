package com.cognizant.Main.service.user;

import com.cognizant.Main.dto.SignupDTO;
import com.cognizant.Main.dto.UserDTO;

public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);


}

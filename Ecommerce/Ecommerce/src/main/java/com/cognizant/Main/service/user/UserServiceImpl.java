package com.cognizant.Main.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cognizant.Main.dto.SignupDTO;
import com.cognizant.Main.dto.UserDTO;
import com.cognizant.Main.entities.Order;
import com.cognizant.Main.entities.User;
import com.cognizant.Main.enums.OrderStatus;
import com.cognizant.Main.enums.UserRole;
import com.cognizant.Main.repository.OrderRepository;
import com.cognizant.Main.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void createAdminAccount()
    {
    	User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
    	if(adminAccount==null)
    	{
    		User user = new User();
    		user.setUserRole(UserRole.ADMIN);
    		user.setEmail("admin@test.com");
    		user.setName("admin");
    		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
    		userRepository.save(user);
    	}
    }
    
    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setUserRole(UserRole.USER);
        
        String password = signupDTO.getpassword();
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        User createdUser = userRepository.save(user);
        Order order = new Order();
        order.setPrice(0L);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUser(createdUser);
        orderRepository.save(order);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setName(createdUser.getName());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setUserRole(createdUser.getUserRole());
        userDTO.setpassword(createdUser.getpassword());
        return userDTO;
    }

	@Override
	public boolean hasUserWithEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findFirstByEmail(email)!=null;
	}

	
}

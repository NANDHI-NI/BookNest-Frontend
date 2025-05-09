package com.cognizant.Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Main.entities.User;
import com.cognizant.Main.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findFirstByEmail(String email);

	User findByUserRole(UserRole admin);
}

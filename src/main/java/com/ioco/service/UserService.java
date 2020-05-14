package com.ioco.service;

import java.util.Set;

import com.ioco.domain.User;
import com.ioco.security.UserRole;

public interface UserService 
{
	 void save(User user);
	 User findByUsername(String username);
	 User findByEmail(String email);
	 boolean checkUserExists(String username,String email);
	 boolean checkUsernameExists(String username);
	 boolean checkEmailExists(String email);
	 
	 User createUser(User user,Set<UserRole> userRoles);
}

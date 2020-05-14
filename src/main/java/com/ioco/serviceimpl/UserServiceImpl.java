package com.ioco.serviceimpl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioco.dao.Dao;
import com.ioco.dao.RoleDao;
import com.ioco.domain.User;
import com.ioco.security.UserRole;
import com.ioco.service.AccountService;
import com.ioco.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private Dao dao;
	
	@Autowired
	private RoleDao roleDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountService accountService;
	
	public void save(User user)
	{
		dao.save(user);
	}
	
	public User findByUsername(String username)
	{
		return dao.findByUsername(username);
	}
	
	public User findByEmail(String email)
	{
		return dao.findByEmail(email);
	}
	
	
	
	public User createUser(User user, Set<UserRole> userRoles)
	{
		User localUser = dao.findByUsername(user.getUsername());
		if(localUser!=null)
		{
			LOG.info("User with username {} already exists ", user.getUsername());
		}
		else
		{
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
		
		
		for(UserRole ur:userRoles)
		{
			roleDao.save(ur.getRole());
		}
		
		user.getUserRoles().addAll(userRoles);
		user.setPrimaryAccount(accountService.createPrimaryAccount());
		user.setSavingsAccount(accountService.createSavingsAccount());
		user.setCreditCard(accountService.creatCreditCard());
		
		localUser = dao.save(user);
		
		}
		
		return localUser;
	}
	
	public boolean checkUserExists(String username, String email)
	{
		if(checkUsernameExists(username) || checkEmailExists(email))
		{
			return true;		
		}
		else
		{
			return false;	
		}
	}
	
	public boolean checkUsernameExists(String username) 
	{
		if(findByUsername(username)!=null)
		{
			return true;
		}
		return false;
	}
	
	public boolean checkEmailExists(String email)
	{
		if(findByEmail(email)!=null)
		{
			return true;
		}
		
		return false;
	}
}

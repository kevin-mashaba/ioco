package com.ioco.dao;

import org.springframework.data.repository.CrudRepository;

import com.ioco.security.Role;

public interface RoleDao extends  CrudRepository<Role, Integer>
{
	Role findByName(String name);
	
}

package com.ioco.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioco.domain.User;
@Repository
public interface Dao extends CrudRepository<User, Long> 
{
	User findByUsername(String username);
	User findByEmail(String email);
}

package com.ioco.dao;

import org.springframework.data.repository.CrudRepository;

import com.ioco.domain.PrimaryAccount;

public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long>
{

	PrimaryAccount findByAccountNumber (int accountNumber);

	
}

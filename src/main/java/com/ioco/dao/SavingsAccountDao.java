package com.ioco.dao;

import org.springframework.data.repository.CrudRepository;

import com.ioco.domain.SavingsAccount;

public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long>
{
	SavingsAccount findByAccountNumber(int accountNumber);
}

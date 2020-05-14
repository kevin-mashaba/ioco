package com.ioco.dao;

import org.springframework.data.repository.CrudRepository;

import com.ioco.domain.CreditCard;

public interface CreditCardDao extends CrudRepository<CreditCard, Long>
{
	CreditCard findByAccountNumber(int accountNumber);
}

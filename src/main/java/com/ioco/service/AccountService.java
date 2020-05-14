package com.ioco.service;

import java.security.Principal;

import com.ioco.domain.CreditCard;
import com.ioco.domain.PrimaryAccount;
import com.ioco.domain.SavingsAccount;

public interface AccountService 
{
	PrimaryAccount createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	CreditCard creatCreditCard();
	void deposit(String accountType, double amount,Principal principal);
	void withdraw(String accountName,String accountType,double amount,Principal principal);
}

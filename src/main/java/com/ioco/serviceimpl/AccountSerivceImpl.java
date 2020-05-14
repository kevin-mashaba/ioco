package com.ioco.serviceimpl;

import java.math.BigDecimal;
import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioco.dao.CreditCardDao;
import com.ioco.dao.PrimaryAccountDao;
import com.ioco.dao.SavingsAccountDao;
import com.ioco.domain.CreditCard;
import com.ioco.domain.PrimaryAccount;
import com.ioco.domain.SavingsAccount;
import com.ioco.domain.User;
import com.ioco.service.AccountService;
import com.ioco.service.UserService;

@Service
public class AccountSerivceImpl implements AccountService{

	private static int nextAccountNumber = 11223145;
	
	private static String accountType = "cheque";
	
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	@Autowired
	private CreditCardDao creditCardDao;
	@Autowired
	private UserService userService;
	
	@Override
	public PrimaryAccount createPrimaryAccount() 
	{
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAcountNumber(accountGen());
		primaryAccount.setAccountType(accountType);
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAcountNumber());
	}

	@Override
	public SavingsAccount createSavingsAccount() 
	{
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}
	
	@Override
	public CreditCard creatCreditCard() 
	{
		CreditCard creditCard = new CreditCard();
		creditCard.setAccountBalance(new BigDecimal(0.0));
		creditCard.setAccountNumber(accountGen());
		
		return creditCardDao.save(creditCard);
	}
	
	public void deposit(String accountName,double amount,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		
		if(accountName.equalsIgnoreCase("Primary"))
		{
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			
		}
		else if(accountName.equalsIgnoreCase("Savings"))
		{
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
		}
		else if(accountName.equalsIgnoreCase("CreditCard"))
		{
			CreditCard creditCard = user.getCreditCard();
			creditCard.setAccountBalance(creditCard.getAccountBalance().add(new BigDecimal(amount)));
			creditCardDao.save(creditCard);
		}
	}
	
	public void withdraw(String accountName,String accountType,double amount,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		CreditCard creditCard = user.getCreditCard();
		double balance = primaryAccount.getAccountBalance().doubleValue();
		double newAmount=0;
		
		String result ="";
		
		if(accountName.equalsIgnoreCase("Primary") || accountType.equalsIgnoreCase("cheque"))
		{
			
			if(amount <= balance  ) 
			{
				
				
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				primaryAccountDao.save(primaryAccount);
			}
			else
			{
				result ="insufficient";
				
			}
			if(amount <= 10000 )
			{
					
				result ="reached limit";
				
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				newAmount = primaryAccount.getAccountBalance().doubleValue();
				
				if(newAmount >= -10000)
				{
					result ="reached limit";
					
					
					primaryAccountDao.save(primaryAccount);
					
				}
				
					
			}

			
	
		}
		else if(accountName.equalsIgnoreCase("Savings") && amount <= savingsAccount.getAccountBalance().doubleValue())
		{
			
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
		
		}
		else if(accountName.equalsIgnoreCase("CreditCard") && amount <= creditCard.getAccountBalance().doubleValue())
		{
			
			creditCard.setAccountBalance(creditCard.getAccountBalance().subtract(new BigDecimal(amount)));
			creditCardDao.save(creditCard);
		}
	}
	
	private int accountGen()
	{
		return ++nextAccountNumber;
	}

	

}

package com.ioco.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ioco.domain.CreditCard;
import com.ioco.domain.PrimaryAccount;
import com.ioco.domain.SavingsAccount;
import com.ioco.domain.User;
import com.ioco.service.AccountService;
import com.ioco.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController 
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		model.addAttribute("primaryAccount",primaryAccount);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model,Principal princiapl)
	{
		User user = userService.findByUsername(princiapl.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		model.addAttribute("savingsAccount",savingsAccount);
		return "savingsAccount";
	}
	
	@RequestMapping("/creditCard")
	public String creditCard(Model model,Principal principal)
	{
		User user = userService.findByUsername(principal.getName());
		CreditCard creditCard =user.getCreditCard();
		model.addAttribute("creditCard",creditCard);
		return "creditCard";
	}
	
	@RequestMapping(value="/deposit", method = RequestMethod.GET)
	public String deposit(Model model)
	{
		model.addAttribute("accountName","");
		model.addAttribute("amount", "");
		
		return "deposit";
	}
	
	@RequestMapping(value="/deposit",method=RequestMethod.POST)
	public String depositPOST(@ModelAttribute("amount") String amount,@ModelAttribute("accountName") String accountName,Principal principal)
	{
		try
		{
			accountService.deposit(accountName,Double.parseDouble(amount), principal);
		}
		catch(Exception e)
		{
			System.out.println("Deposit error: " + e.getMessage());
		}
			
		
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value="/withdraw",method=RequestMethod.GET)
	public String withdraw(Model model)
	{
		model.addAttribute("accountName","");
		model.addAttribute("amount","");
		return "withdraw";
	}
	
	@RequestMapping(value="/withdraw",method=RequestMethod.POST)
	public String withdrawPOST(@ModelAttribute("amount") String amount,@ModelAttribute("accountName") String accountName,
			@ModelAttribute("accountType") String accountType,Principal principal,Model model)
	{
		User user = userService.findByUsername(principal.getName());
		try
		{
			
			accountService.withdraw(accountName,accountType,Double.parseDouble(amount),principal);
			
		}
		catch(Exception e)
		{
			System.out.println("Withdraw error: " + e.getMessage());

		}
		
		
		return "redirect:/dashboard";
	}
}

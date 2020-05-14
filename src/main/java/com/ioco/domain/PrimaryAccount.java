package com.ioco.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PrimaryAccount 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int accountNumber;
	private BigDecimal accountBalance;
	private String accountType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAcountNumber() {
		return accountNumber;
	}

	public void setAcountNumber(int acountNumber) {
		this.accountNumber = acountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	
	
}

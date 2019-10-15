package com.rabobank.reports.model;

import java.io.Serializable;

public class CustomerStatement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String reference;
	private String accountNumber;
	private String description;
	private Double startBalance;
	private Double mutation;
	private Double endBalance;
	
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}
	public Double getMutation() {
		return mutation;
	}
	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}
	public Double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		CustomerStatement customerReport = (CustomerStatement) obj;
		return this.reference.equals(customerReport.getReference());
	}
	
	@Override
	public String toString() {
		return "CustomerTransactionReport [reference=" + reference + ", accountNumber=" + accountNumber
				+ ", description=" + description + ", startBalance=" + startBalance + ", mutation=" + mutation
				+ ", endBalance=" + endBalance + "]";
	}
	
	@Override
	public int hashCode() {
		return this.reference.hashCode();
	}
	
	
	
	
	
}

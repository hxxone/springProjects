package com.green.vo;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class FreeVO {

	private String free_name;
	private String free_password;
	private String free_serial;
	private Float free_deposit;
	private Float free_withdrawal;
	private Float free_balance;
	private boolean loan;
	private Long free_no;
	
	public FreeVO(){
		this.free_deposit = 0f;
		this.free_withdrawal =0f;
		this.free_balance =0f;
		this.loan = false;
	}
	

	
//	public Float getBalace(){
//		
//		return this.free_balance +=this.free_deposit - this.free_withdrawal;		
//	}
//	
//	public boolean setIsLoan() {
//		this.loan = (this.free_balance>=5.0f);
//		return this.loan;
//	}
	
	
//	public void setFree_balance() {
//		this.free_balance = this.free_balance + this.free_deposit - this.free_withdrawal;
//	}
	
//	public void setLoan() {
//		this.loan = (this.free_balance>=5);
//	}
//	
	
//	public void setFree_deposit(Float free_deposit) {
//		this.free_deposit = free_deposit;
//		this.free_balance += free_deposit;
//	}
//	public void setFree_withdrawal(Float free_withdrawal) {
//		this.free_withdrawal = free_withdrawal;
//		this.free_balance =+ free_withdrawal;
//	}
//	
//	public boolean getLoan() {
//		
//		return (this.free_balance>=5.0f);
//	}
	
//	public float getFree_balance() {
//		this.free_balance += this.getFree_deposit();
//		this.free_balance -= this.getFree_withdrawal();	
//		return this.free_balance;
//	}
	
//	public void setIsLoan() {
//		this.isLoan = (this.free_balance>=5.0f);
////		return this.isLoan;
//	}
	
}

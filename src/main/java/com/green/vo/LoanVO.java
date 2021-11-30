package com.green.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanVO {
	
	private Long loan_no;
	private String loan_name;
    private Float loan_total;
    private Float loan_fee;
    private Float loan_balance;

}

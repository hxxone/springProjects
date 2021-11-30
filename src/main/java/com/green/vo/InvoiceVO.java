package com.green.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceVO {
	
	private Long invoice_id;
	private String invoice_name;
	private String invoice_password;
	private String invoice_address;
	private String invoice_detail_addr;
	private String invoice_mobile_phone;
	private String invoice_number;
	private String product;
	private float price;
	private Long quantity;
	private float total;
	private String coupon;
	private String coupon_use;
	
}

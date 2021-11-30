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
@Builder
public class UserVO {
	
	 
	private Long user_id;
	private String user_name;
	private String user_password;
	private String user_address;
	private String user_detail_addr;
	private String user_resident;
	private String telephone;
	private String mobile_phone;

}

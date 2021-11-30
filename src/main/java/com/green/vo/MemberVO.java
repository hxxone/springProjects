package com.green.vo;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberVO {

	 private String userid;
	 private String userpw;
	 private String username;
	 private boolean enabled;
	 
	 private Date regdate;
	 private Date updatedate;

	 private List<AuthVO> authList; // 사용자마다 권한이 여러개 일 수 있음
}

package com.green.vo;

import java.util.Date;

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
public class ReplyVO {
	private Long rno;
	private Long bno; // board의 bno와 fk
	private String reply;
	private String replyer;
	private Date replyDate;
	private Date updateDate;
	

}

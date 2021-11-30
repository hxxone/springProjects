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
public class BoardAttachVO {
	 private String uuid;
	 private String uploadPath;
	 private String fileName;
	 private boolean fileType; //이미지 여부
	 
	 private Long bno; 
}

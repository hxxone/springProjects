package com.green.vo;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type; // 검색을 위한 타입( 제목(T), 컨텐츠(C), 저자(W) ,wc => 조합도 가능)
	private String keyword; // 검색어
	public Criteria() {
		this(1,10); //1페이지, 페이지당 10개 씩 기본 설정
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum=pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type==null? new String[] {} : type.split("");//""을 기준으로 분리함 => 문자열을 문자 단위로 분리
	}
	
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		String result = builder.toUriString();
		System.out.println("Uri==========================================================="+ result);
				
		return result;
	}
}

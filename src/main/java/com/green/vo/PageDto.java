package com.green.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;// 총 레코드 갯수
	private Criteria cri; // 페이지 넘버, 페이지당 갯수
	
	public PageDto(Criteria cri, int total) {
		this.cri=cri;
		this.total=total;
		
		this.endPage=(int)(Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage = this.endPage-9;
		int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
		if(realEnd <this.endPage) this.endPage=realEnd;
		this.prev =this.startPage>1;
		this.next = this.endPage<realEnd;
	}
	
	
	

}

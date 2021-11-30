package com.green.mapper;

import java.util.List;

import com.green.vo.InvoiceVO;

public interface InvoiceMapper {
	
	public int purchace(InvoiceVO vo);
	public List<InvoiceVO> getInvoiceById(Long id);
	public int remove(String invoice_num);
}

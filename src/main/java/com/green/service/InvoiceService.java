package com.green.service;

import java.util.List;

import com.green.vo.InvoiceVO;

public interface InvoiceService {
	
	public int purchace(InvoiceVO vo);
	public List<InvoiceVO> getInvoiceById(Long id);
	public int remove(String invoice_num);

}

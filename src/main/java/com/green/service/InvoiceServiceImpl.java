package com.green.service;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.mapper.InvoiceMapper;
import com.green.vo.InvoiceVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService{
	
	static int cnt=0;
	
	@Setter(onMethod_=@Autowired)
	InvoiceMapper mapper;

	@Override
	public int purchace(InvoiceVO vo) {
		System.out.println("vo" + vo);
		
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-ss");
		String datestr = dateFormat.format(date);
		String invoiceNumber = vo.getInvoice_id()+"-"+vo.getInvoice_name()+datestr;
		vo.setInvoice_number(invoiceNumber);
		System.out.println(" 서비스 " + vo);
		return mapper.purchace(vo);
	}

	@Override
	public List<InvoiceVO> getInvoiceById(Long id) {
		
		return mapper.getInvoiceById(id);
	}

	@Override
	public int remove(String invoice_num) {
		
		return mapper.remove(invoice_num);
	}

}

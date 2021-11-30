package com.green.mapper;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.green.vo.InvoiceVO;
import com.green.vo.UserVO;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Slf4j
public class UserMapperTests {
	
	@Setter(onMethod_=@Autowired)
	UserMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	InvoiceMapper invoice;
	
	
	@Test
	public void remove() {
		List<InvoiceVO> list = invoice.getInvoiceById(3L);
		String numString = list.get(0).getInvoice_number();
		invoice.remove(numString);
	}
	
//	@Test
	public void invoice() {
		invoice.getInvoiceById(3L);
		
	}
	
//	@Test 
	public void insertTest() {
		
		InvoiceVO vo = new InvoiceVO();
		UserVO user = mapper.getUser(3L);
		vo.setInvoice_name(user.getUser_name());
		vo.setInvoice_id(user.getUser_id());
		vo.setInvoice_password(user.getUser_password());
		vo.setInvoice_address(user.getUser_address());
		vo.setInvoice_detail_addr(user.getUser_detail_addr());
		vo.setInvoice_mobile_phone(user.getMobile_phone());
		vo.setInvoice_number("1234");
		vo.setProduct("set");
		vo.setPrice(13.0f);
		vo.setQuantity(2L);
		vo.setTotal(23f);
		vo.setCoupon_use("Y");
		invoice.purchace(vo);
		
	}
	
//	@Test
	public void signUnTest() {
		UserVO userVO = UserVO.builder()
							.user_name("test")
							.user_password("test")
							.user_address("test")
							.user_detail_addr("test")
							.user_resident("111-111")
							.mobile_phone("1111")
							.telephone("2122")
							.build();
		mapper.signUp(userVO);
	
		
	}
	
//	@Test
	public void getAll() {
		mapper.getAllUser();
	}
	
//	@Test
	public void getUser() {
		mapper.getUser(3L);
	}

}

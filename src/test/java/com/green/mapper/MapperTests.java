package com.green.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.green.vo.FreeVO;
import com.green.vo.LoanVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import oracle.security.o3logon.a;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MapperTests {
	@Setter(onMethod_=@Autowired)
	FreeMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	LoanMapper loanMapper;
	
//	@Test
	public void testDeposit() {
			FreeVO vo = new FreeVO();
			
			vo.setFree_name("test2");
			vo.setFree_password("test2");
			vo.setFree_serial("test_serial2");	
			vo.setFree_deposit(0f);
			vo.setFree_withdrawal(600f);
//			vo.setFree_balance(0f);
			float balance = mapper.getBalance("test_serial2").getFree_balance();
			vo.setFree_balance((balance + vo.getFree_deposit() -vo.getFree_withdrawal()));
			mapper.depositAndWithdrawal(vo);
			mapper.loan(vo);

	}
	
//	@Test
	public void testGetLoan() {
		
		LoanVO vo = new LoanVO();
		vo.setLoan_name("hihi");
		vo.setLoan_total(5000f);
		loanMapper.getloan(vo);
	}
	
//	@Test
	public void testGetList() {
		loanMapper.getList("test0");
	}
	
	@Test
	public void testborrow() {
		LoanVO vo=new LoanVO();
		vo.setLoan_fee(50f);
		vo.setLoan_balance(60f);
		
		loanMapper.borrow(vo);
	}
	
	
//	@Test
	public void testSerial() {
		List<FreeVO> list = mapper.getListBySerial("test_serial2");
		float balance = (float)(list.stream().mapToDouble( i -> i.getFree_deposit()).sum())
			- (float)(list.stream().mapToDouble(i -> i.getFree_withdrawal()).sum());
		System.out.println("==========================================================");
		System.out.println(balance);
	}
//	@Test
	public void testGetBalance() {
		mapper.getBalance("test_serial2");
		
	}
	
	
////	@Test
//	public void testDeposit2() {
//		FreeVO vo = new FreeVO();
//		vo.setFree_name("test10");
//		vo.setFree_password("test10");
//		vo.setFree_deposit(5f);
//		vo.setFree_withdrawal(2f);
//		vo.setFree_balance();
//		vo.setLoan();
//		vo.setFree_serial("test_serial10");
//		
//		mapper.depositAndWithdrawal2(vo);
//	}
//	
//	@Test
//	public void testBalance() {
//		Long fNo = mapper.getLatestNo("test_serial2");
//		FreeVO vo = mapper.checkAccount(7L);
//		
//		mapper.updateBalance(vo);
//	
//	}
	
//	@Test
	public void testIsLoan() {
		Long fNo = mapper.getLatestNo("test_serial");
		FreeVO vo = mapper.checkAccount(fNo);
		vo.setFree_no(fNo);
//		mapper.isloan(vo);
	}
//	
	
//	@Test
	public void test0() {
		
		mapper.balancePerSerial("test0");
	}
	
//	@Test
	public void testGetAccount() {
		
		for(int i=0; i<10; i++) {
			FreeVO vo = new FreeVO();
			vo.setFree_name("test"+i);
			vo.setFree_password("test"+i);
			vo.setFree_serial("testserial"+i);
			mapper.test(vo);
		}
	}
	
//	@Test
	public void testBankBook() {
		
		mapper.bankBook("test");
	}
	
	
//	@Test
	public void testGetAll() {
		mapper.getNameList();
	}
	
//	@Test
	public void testGetSereal() {
		
		FreeVO vo = mapper.checkAccount(11L);
		vo.setFree_serial("hihi");
		mapper.getSerial(vo);
	}

}

package com.green.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.mapper.BoardAttachMapper;
import com.green.mapper.BoardMapper;
import com.green.mapper.ReplyMapper;
import com.green.vo.BoardAttachVO;
import com.green.vo.BoardVO;
import com.green.vo.Criteria;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class BoardServiceImpl implements BoardService{
	
	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_=@Autowired)
	private ReplyMapper replymapper;
		
	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("서비스등록" + board);
		mapper.insertSelectKey(board);
		// 외래키 속성을 다루는 중요한 로직
		//  각 게시물의 bno에 있는 파일의 bno가 외래키로 연결 되어 있으므로 첨부파일을 추가 할  수 있다
		if(board.getAttachList() == null || board.getAttachList().size()==0) return;
		board.getAttachList().forEach( attachVO -> {
			attachVO.setBno(board.getBno());
			attachMapper.insert(attachVO);
		});
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("2)========================서비스 ");
		return mapper.read(bno);
	}
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		log.info("2)서비스에서 수정 " + board);
		// 첨부파일 전체 삭제
		attachMapper.deleteAll(board.getBno());
		boolean modifyResult = mapper.update(board)==1;
		// && 앞의 값이 true면 && 다음 조건문을 확인, false면 즉시 break; : short circuit
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size()>0) {
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
	
	@Transactional
	@Override
	public boolean remove(Long bno) {
		log.info("2)서비스에서 데이터 삭제 : " + bno);
		attachMapper.deleteAll(bno);
		replymapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;
	}

//	@Override
//	public List<BoardVO> getList() {
//		log.info("2) 전체목록가져오기=========================================");
//		return mapper.getList();
//	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("2) 서비스에서 크리테리아를 이용해 전체 목록 가져오기 ==========================" + cri);
		
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("2)======================= 크리테리아에서 전체 데이터 갯수 구하기 " );
	
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info("======================= 2) 서비스에서 bno에 해당하는 파일 전체 목록 " + bno);
		
		return attachMapper.findByBno(bno);
	}

	
	
}

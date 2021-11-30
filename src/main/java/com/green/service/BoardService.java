package com.green.service;

import java.util.List;

import com.green.vo.BoardAttachVO;
import com.green.vo.BoardVO;
import com.green.vo.Criteria;
import com.green.vo.ReplyVO;

public interface BoardService {
	public void register(BoardVO board); //3) mybatis를 통해 데이터를 가져옴
	public BoardVO get(Long bno);
	public boolean modify(BoardVO board);
	public boolean remove(Long bno);
//	public List<BoardVO> getList();
	
	public List<BoardVO> getList(Criteria cri);
	public int getTotal(Criteria cri);


	public List<BoardAttachVO> getAttachList(Long bno);

	
	
}

package com.green.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.green.vo.BoardVO;
import com.green.vo.Criteria;

public interface BoardMapper {
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board);
	public BoardVO read (Long bno);
	public int delete(Long bno);
	public int update(BoardVO board);

	public int getTotalCount(Criteria cri);//322p
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}

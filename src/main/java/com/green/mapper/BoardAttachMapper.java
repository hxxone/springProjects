package com.green.mapper;

import java.util.List;

import com.green.vo.BoardAttachVO;

public interface BoardAttachMapper {
	
	public void insert(BoardAttachVO vo);
	public void delete(String uuid);
	public List<BoardAttachVO> findByBno(Long bno);
	public void deleteAll(Long bno);
	
	// 자동으로 cron 실행 테스트 확인
	
	public List<BoardAttachVO> getOldFiles();

}

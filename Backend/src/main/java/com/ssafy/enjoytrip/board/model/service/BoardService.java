package com.ssafy.enjoytrip.board.model.service;

import java.util.Map;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardListDto;

public interface BoardService {

	void writeBoard(BoardDto boardDto) throws Exception;
	BoardListDto listBoard(Map<String, String> map) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getBoard(int boardNo) throws Exception;
	void updateHit(int boardNo) throws Exception;
	
	void modifyBoard(BoardDto boardDto) throws Exception;
//	
	void deleteBoard(int boardNo) throws Exception;
	
}

package com.ssafy.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardListDto;
import com.ssafy.enjoytrip.board.model.CommentListDto;

public interface BoardService {

	int writeBoard(BoardDto boardDto) throws Exception;
	BoardListDto listBoard(Map<String, String> map) throws Exception;
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getBoard(int boardNo) throws Exception;
	void updateHit(int boardNo) throws Exception;
	
	void modifyBoard(BoardDto boardDto) throws Exception;
	void deleteBoard(int boardNo) throws Exception;
	CommentListDto listComment(Map<String, String> map) throws Exception;
	void deleteBoardComment(int boardNo) throws Exception;
	void deleteComment(int commentno) throws Exception;
	void modifyComment(Map<String, String> map) throws Exception;
	void registerComment(Map<String, String> map) throws Exception;
	List<Map<String, Object>> getUserBoardList(Map<String, Object> map);
	
}

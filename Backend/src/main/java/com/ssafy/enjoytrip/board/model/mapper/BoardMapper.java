package com.ssafy.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.CommentDto;
//import com.ssafy.enjoytrip.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {

	int writeBoard(BoardDto boardDto) throws SQLException;

//	void registerFile(BoardDto boardDto) throws Exception;

	List<BoardDto> listBoard(Map<String, Object> param) throws SQLException;

	int getTotalBoardCount(Map<String, Object> param) throws SQLException;

	BoardDto getBoard(int boardNo) throws SQLException;

	void updateHit(int boardNo) throws SQLException;

	void modifyBoard(BoardDto boardDto) throws SQLException;

//	void deleteFile(int boardNo) throws Exception;

	void deleteBoard(int boardNo) throws SQLException;
	
	void deleteBoardComment(int boardNo) throws SQLException;

//	List<FileInfoDto> fileInfoList(int boardNo) throws Exception;
	
	List<CommentDto> listComment(int boardNo) throws SQLException;

	void deleteComment(int commentno) throws SQLException;

	void modifyComment(Map<String, String> map) throws SQLException;

	void registerComment(Map<String, String> map) throws SQLException;

	List<Map<String, Object>> getUserBoardList(Map<String, Object> map);
}

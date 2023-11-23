package com.ssafy.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardListDto;
import com.ssafy.enjoytrip.board.model.CommentDto;
import com.ssafy.enjoytrip.board.model.CommentListDto;
//import com.ssafy.enjoytrip.board.model.FileInfoDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	@Transactional
	public int writeBoard(BoardDto boardDto) throws Exception {
		boardMapper.writeBoard(boardDto);
		int boardno = boardDto.getBoardNo();
		return boardno;
//		List<FileInfoDto> fileInfos = boardDto.getFileInfos();
//		if (fileInfos != null && !fileInfos.isEmpty()) {
//			boardMapper.registerFile(boardDto);
//		}
	}

	@Override
	public BoardListDto listBoard(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");
		List<BoardDto> list = boardMapper.listBoard(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalBoardCount = boardMapper.getTotalBoardCount(param);
		int totalPageCount = (totalBoardCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setBoardList(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

//	@Override
//	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
//		PageNavigation pageNavigation = new PageNavigation();
//
//		int naviSize = SizeConstant.NAVIGATION_SIZE;
//		int sizePerPage = SizeConstant.LIST_SIZE;
//		int currentPage = Integer.parseInt(map.get("pgno"));
//
//		pageNavigation.setCurrentPage(currentPage);
//		pageNavigation.setNaviSize(naviSize);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
//		param.put("key", key == null ? "" : key);
//		param.put("word", map.get("word") == null ? "" : map.get("word"));
//		int totalCount = boardMapper.getTotalBoardCount(param);
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
//		pageNavigation.setTotalPageCount(totalPageCount);
//		boolean startRange = currentPage <= naviSize;
//		pageNavigation.setStartRange(startRange);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//
//		return pageNavigation;
//	}

	@Override
	public BoardDto getBoard(int boardNo) throws Exception {
		return boardMapper.getBoard(boardNo);
	}

	@Override
	public void updateHit(int boardNo) throws Exception {
		boardMapper.updateHit(boardNo);
	}

	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyBoard 호출
		boardMapper.modifyBoard(boardDto);
	}

//	@Override
//	@Transactional
//	public void deleteBoard(int boardNo, String path) throws Exception {
//		// TODO : BoardDaoImpl의 deleteBoard 호출
//		List<FileInfoDto> fileList = boardMapper.fileInfoList(boardNo);
//		boardMapper.deleteFile(boardNo);
//		boardMapper.deleteBoard(boardNo);
//		for(FileInfoDto fileInfoDto : fileList) {
//			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
//			file.delete();
//		}
//	}

	@Override
	public void deleteBoard(int boardNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteBoard 호출
		boardMapper.deleteBoard(boardNo);
	}
	
	@Override
	public void deleteBoardComment(int boardNo) throws Exception {
		boardMapper.deleteBoardComment(boardNo);
	}

	@Override
	public CommentListDto listComment(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		int boardNo = Integer.parseInt(map.get("boardno"));
		List<CommentDto> list = boardMapper.listComment(boardNo);
		CommentListDto commentListDto = new CommentListDto();
		commentListDto.setCommentList(list);
		return commentListDto;
	}

	@Override
	public void deleteComment(int commentno) throws Exception {
		boardMapper.deleteComment(commentno);
	}

	@Override
	public void modifyComment(Map<String, String> map) throws Exception {
		System.out.println(map.get("commentno"));
		System.out.println(map.get("content"));
		boardMapper.modifyComment(map);
	}

	@Override
	public void registerComment(Map<String, String> map) throws Exception {
		boardMapper.registerComment(map);
	}

	public List<Map<String, Object>> getUserBoardList(Map<String, Object> map) {
		return boardMapper.getUserBoardList(map);
	}

}

package com.ssafy.enjoytrip.board.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.BoardListDto;
import com.ssafy.enjoytrip.board.model.CommentListDto;
import com.ssafy.enjoytrip.board.model.service.BoardService;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

// 변수명 board, board_info 결정
// 파일 테이블
// 다중 파일
// 스웨거
// 테스트 코드
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
@Slf4j
public class BoardController {

	private BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeBoard(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) {
		log.info("writeBoard boardDto - {}", boardDto);
		try {
			int boardno = boardService.writeBoard(boardDto);
			System.out.println("######################" + boardno);
//			return ResponseEntity.ok().build();
//			return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getGugunInSido(sido), HttpStatus.OK);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(boardno);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping("/comment")
	public ResponseEntity<?> registerComment(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) Map<String, String> map) {
		log.info("registerComment - 호출", map);
		try {
			boardService.registerComment(map);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listBoard(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		log.info("listBoard map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listBoard(map);
			System.out.println(boardListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "글 댓글목록", notes = "해당 글의 모든 댓글을 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "댓글목록 OK!!"), @ApiResponse(code = 404, message = "댓글오류!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping("/comment")
	public ResponseEntity<?> listComment(
			@RequestParam @ApiParam(value = "댓글을 얻기위한 글정보.", required = true) Map<String, String> map) {
		log.info("listComment map - {}", map);
		try {
			CommentListDto commentListDto = boardService.listComment(map);
			System.out.println(commentListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(commentListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{boardno}")
	public ResponseEntity<BoardDto> getBoard(
			@PathVariable("boardno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int boardno)
			throws Exception {
		log.info("getBoard - 호출 : " + boardno);
		boardService.updateHit(boardno);
		return new ResponseEntity<BoardDto>(boardService.getBoard(boardno), HttpStatus.OK);
	}

	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{boardno}")
	public ResponseEntity<BoardDto> getModifyBoard(
			@PathVariable("boardno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int boardno)
			throws Exception {
		log.info("getModifyBoard - 호출 : " + boardno);
		return new ResponseEntity<BoardDto>(boardService.getBoard(boardno), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyBoard(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		log.info("modifyBoard - 호출 {}", boardDto);
		
		boardService.modifyBoard(boardDto);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{boardno}")
	public ResponseEntity<String> deleteBoard(@PathVariable("boardno") @ApiParam(value = "살제할 글의 글번호.", required = true) int boardno) throws Exception {
		log.info("deleteBoard - 호출");
		boardService.deleteBoardComment(boardno);
		boardService.deleteBoard(boardno);
		return ResponseEntity.ok().build();

	}
	
	@ApiOperation(value = "게시판 특정 댓글삭제", notes = "댓글번호에 해당하는 댓글을 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/comment")
	public ResponseEntity<?> deleteComment(@RequestParam @ApiParam(value = "특정 댓글을 얻기위한 댓글정보.", required = true) Map<String, String> map) throws Exception {
		log.info("deleteComment - 호출");
		boardService.deleteComment(Integer.parseInt(map.get("commentno")));
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "게시판 특정 댓글수정", notes = "댓글번호에 해당하는 댓글을 수정한다. 그리고 DB삭제 수정여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/comment")
	public ResponseEntity<?> modifyComment(@RequestBody @ApiParam(value = "특정 댓글을 얻기위한 댓글정보.", required = true) Map<String, String> map) throws Exception {
		log.info("modifyComment - 호출");
		System.out.println("###################################################");
		System.out.println(map);
		boardService.modifyComment(map);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "게시판 상세정보까지 요청", notes = "게시판과 게시판 세부정보를 요청한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<Map<String, Object>>> getUserBoardList( @ApiParam(value = "UserInfo가 들어온다.", required = true) @RequestParam Map<String, Object> map) throws Exception {
		log.info("modifyComment - 호출");
		System.out.println("###############map" + map);
		return new ResponseEntity<List<Map<String, Object>>>( boardService.getUserBoardList(map), HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
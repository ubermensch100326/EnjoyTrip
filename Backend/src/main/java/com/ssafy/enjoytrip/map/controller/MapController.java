package com.ssafy.enjoytrip.map.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.map.model.AttractionDto;
import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;
import com.ssafy.enjoytrip.map.model.service.MapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/map")
@Api("Map 컨트롤러  API V1")
@Slf4j
public class MapController {
	
	private MapService mapService;

	public MapController(MapService mapService) {
		super();
		this.mapService = mapService;
	}

	// git test
	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		log.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "시도에 속한 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido
			) throws Exception {
		log.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관광지 정보", notes = "시도,구군,검색어를 받아서 관광지 정보를 반환한다.", response = List.class)
	@GetMapping("/attraction")
	public ResponseEntity<List<AttractionDto>> attraction(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido,
			@RequestParam("gugun") @ApiParam(value = "구군코드.", required = true) String gugun,
			@RequestParam("keyword") @ApiParam(value = "검색어.", required = false) String keyWord,
			@RequestParam("numOfRows") @ApiParam(value = "개수.", required = true) String numOfRows,
			@RequestParam("typeNum") @ApiParam(value = "관광지 유형", required = false) String typeNum
			) throws Exception {
		
		Map map = new HashMap<String, String>();
		
		if (sido != null)
			map.put("sido", sido);
		if (gugun != null)
			map.put("gugun", gugun);
		if (keyWord != null)
			map.put("keyword", keyWord);
		if (numOfRows != null)
			map.put("numOfRows", numOfRows);
		if (typeNum != null)
			map.put("typeNum", typeNum);
		
		return new ResponseEntity<List<AttractionDto>> (mapService.getAttractionList(map), HttpStatus.OK);
	}
	
}

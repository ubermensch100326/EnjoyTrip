package com.ssafy.enjoytrip.map.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;

public interface MapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<Map<String, String>> getAttractionList(Map<String, String> sidogugun) throws Exception;
	void createUserPlan(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> getUserPlan(Integer board_no) throws Exception;
	void deleteUserPlan(Integer board_no);
	
}

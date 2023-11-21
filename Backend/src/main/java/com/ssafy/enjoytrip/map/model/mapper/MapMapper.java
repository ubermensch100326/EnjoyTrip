package com.ssafy.enjoytrip.map.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;

public interface MapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<Map<String, String>> getAttractionList(Map<String, String> sidogugun) throws SQLException;
	Map<String,Object> createUserPlan(Map<String, Object> map) throws SQLException;
}

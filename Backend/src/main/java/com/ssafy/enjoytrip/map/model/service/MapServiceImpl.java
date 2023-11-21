package com.ssafy.enjoytrip.map.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.map.model.SidoGugunCodeDto;
import com.ssafy.enjoytrip.map.model.mapper.MapMapper;

@Service
public class MapServiceImpl implements MapService {
	
	private MapMapper mapMapper;

	public MapServiceImpl(MapMapper mapMapper) {
		super();
		this.mapMapper = mapMapper;
	}

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return mapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return mapMapper.getGugunInSido(sido);
	}

	@Override
	public List<Map<String, String>> getAttractionList(Map<String, String> sidogugun) throws Exception {
		return mapMapper.getAttractionList(sidogugun);
	}

	@Override
	public void createUserPlan(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		mapMapper.createUserPlan(map);
	}

}

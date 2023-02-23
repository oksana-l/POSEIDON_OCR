package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointFormDTO;

public interface CurvePointService {

	List<CurvePoint> findAllCurvePoint();

	CurvePoint create(CurvePointFormDTO curvePointFormDto);

	CurvePointFormDTO getCurvePointById(Integer id);

	CurvePoint update(Integer id, CurvePointFormDTO curvePointFormDto);
	
	void deleteCurvePointById(Integer id);
	
	boolean ifCurvePointExists(Integer id);
}

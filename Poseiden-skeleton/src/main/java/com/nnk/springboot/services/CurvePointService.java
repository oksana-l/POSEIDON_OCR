package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurveFormDTO;

public interface CurvePointService {

	List<CurvePoint> findAllCurvePoint();

	CurvePoint create(CurveFormDTO curveFormDto);

	CurveFormDTO getCurvePointById(Integer id);

	CurvePoint update(Integer id, CurveFormDTO curveFormDto);
	
	void deleteCurvePointById(Integer id);

}

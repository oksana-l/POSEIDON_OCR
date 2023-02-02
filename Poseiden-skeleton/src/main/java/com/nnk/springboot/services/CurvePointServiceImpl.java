package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurveFormDTO;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointServiceImpl implements CurvePointService {

	private CurvePointRepository curvePointRepository;
	
	public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
		super();
		this.curvePointRepository = curvePointRepository;
	}

	@Override
	public List<CurvePoint> findAllCurvePoint() {
		
		return curvePointRepository.findAll();
	}

	@Override
	public CurvePoint create(CurveFormDTO curveFormDto) {
		CurvePoint newCurveFormDto = new CurvePoint();
		newCurveFormDto.setCurveId(curveFormDto.getCurveId());
		newCurveFormDto.setTerm(curveFormDto.getTerm());
		newCurveFormDto.setValue(curveFormDto.getValue());
		return curvePointRepository.save(newCurveFormDto);
	}

	@Override
	public CurveFormDTO getCurvePointById(Integer id) {
		
		return new CurveFormDTO(curvePointRepository.getCurvePointById(id));
	}

	@Override
	public CurvePoint update(Integer id, CurveFormDTO curveFormDto) {
		CurvePoint curvePoint = curvePointRepository.getCurvePointById(id);
		curvePoint.setCurveId(curveFormDto.getCurveId());
		curvePoint.setTerm(curveFormDto.getTerm());
		curvePoint.setValue(curveFormDto.getValue());
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public void deleteCurvePointById(Integer id) {
		curvePointRepository.deleteById(id);
		
	}

}

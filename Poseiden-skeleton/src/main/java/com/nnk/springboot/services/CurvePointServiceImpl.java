package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointFormDTO;
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
	public CurvePoint create(CurvePointFormDTO curvePointFormDto) {
		CurvePoint newCurveFormDto = new CurvePoint();
		newCurveFormDto.setCurveId(curvePointFormDto.getCurveId());
		newCurveFormDto.setTerm(curvePointFormDto.getTerm());
		newCurveFormDto.setValue(curvePointFormDto.getValue());
		return curvePointRepository.save(newCurveFormDto);
	}

	@Override
	public CurvePointFormDTO getCurvePointById(Integer id) {
		return new CurvePointFormDTO(curvePointRepository.getCurvePointById(id));
	}

	@Override
	public CurvePoint update(Integer id, CurvePointFormDTO curvePointFormDto) {
		CurvePoint curvePoint = curvePointRepository.getCurvePointById(id);
		curvePoint.setCurveId(curvePointFormDto.getCurveId());
		curvePoint.setTerm(curvePointFormDto.getTerm());
		curvePoint.setValue(curvePointFormDto.getValue());
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public void deleteCurvePointById(Integer id) {
		curvePointRepository.deleteById(id);
	}

	@Override
	public boolean ifCurvePointExists(Integer id) {
		
		return curvePointRepository.existsById(id);
	}

}

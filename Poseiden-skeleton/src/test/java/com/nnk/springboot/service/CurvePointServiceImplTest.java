package com.nnk.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurveFormDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import com.nnk.springboot.services.CurvePointServiceImpl;

public class CurvePointServiceImplTest {


	private CurvePointRepository curvePointRepository;
	private CurvePointService curvePointService;
	
	@BeforeEach
	public void setUp() {
		curvePointRepository = mock(CurvePointRepository.class);
		curvePointService = new CurvePointServiceImpl(curvePointRepository);
		
	}
	
	@Test
	public void shouldFindAllCurvePoint() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(2);
		List<CurvePoint> curvePointList= new ArrayList<CurvePoint>();
		curvePointList.add(curvePoint);
		
		when(curvePointRepository.findAll()).thenReturn(curvePointList);
		
		List<CurvePoint> newCurvePointList = curvePointService.findAllCurvePoint();

		Assertions.assertEquals(curvePointList.isEmpty(), newCurvePointList.isEmpty());
		Assertions.assertEquals(curvePointList.size(), newCurvePointList.size());
		Assertions.assertEquals(2, newCurvePointList.get(0).getCurveId());
	}
	
	@Test
	public void shouldCreate() {
		CurveFormDTO curvePointDto = new CurveFormDTO();
		curvePointDto.setCurveId(1);
		curvePointDto.setTerm(3d);
		curvePointDto.setValue(5d);
		
		when(curvePointRepository.save(any())).thenReturn(new CurvePoint(curvePointDto));
		
		CurvePoint savedCurvePoint = curvePointService.create(curvePointDto);
		
		Assertions.assertEquals(1, savedCurvePoint.getCurveId());
		Assertions.assertEquals(3d, savedCurvePoint.getTerm());
		Assertions.assertEquals(5d, savedCurvePoint.getValue());
		
	}
	
	@Test
	public void shouldGetBidById() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(1);
		curvePoint.setCurveId(1);
		curvePoint.setTerm(3d);
		curvePoint.setValue(5d);
		
		when(curvePointRepository.getCurvePointById(anyInt())).thenReturn(curvePoint);
		
		CurveFormDTO curvePointDto = curvePointService.getCurvePointById(1);
		
		Assertions.assertEquals(1, curvePointDto.getId());
		Assertions.assertEquals(1, curvePointDto.getCurveId());
		Assertions.assertEquals(3d, curvePointDto.getTerm());
		Assertions.assertEquals(5d, curvePointDto.getValue());		
	}
	
	@Test
	public void shouldUpdate() {
		CurveFormDTO curvePointDto = new CurveFormDTO();
		curvePointDto.setCurveId(1);;
		curvePointDto.setTerm(2d);;
		curvePointDto.setValue(5d);
		
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurveId(3);
		curvePoint.setTerm(5d);
		curvePoint.setValue(3d);
		
		when(curvePointRepository.getCurvePointById(anyInt())).thenReturn(curvePoint);	
		when(curvePointRepository.save(any())).thenReturn(new CurvePoint(curvePointDto));
		
		CurvePoint updatedCurvePoint = curvePointService.update(1, curvePointDto);
		
		Assertions.assertEquals(1, updatedCurvePoint.getCurveId());
		Assertions.assertEquals(2d, updatedCurvePoint.getTerm());
		Assertions.assertEquals(5d, updatedCurvePoint.getValue());
	}
	
	@Test
	public void shouldDeleteCurvePointById() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setId(1);
		
		curvePointService.deleteCurvePointById(1);
		
		verify(curvePointRepository).deleteById(1);
	}
}

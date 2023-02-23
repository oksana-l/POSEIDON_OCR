package com.nnk.springboot.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.dto.RuleFormDTO;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.services.RuleService;
import com.nnk.springboot.services.RuleServiceImpl;

public class RuleServiceImplTest {
	
	private RuleRepository ruleRepository;
	private RuleService ruleService;
	private RuleFormDTO ruleDto;
	
	@BeforeEach
	public void setUp() {
		ruleRepository = mock(RuleRepository.class);
		ruleService = new RuleServiceImpl(ruleRepository);
		
	}
	
	@Test
	public void shouldFindAllRuleTest() {
		Rule rule = new Rule();
		rule.setDescription("Description");
		List<Rule> ruleList= new ArrayList<Rule>();
		ruleList.add(rule);
		
		when(ruleRepository.findAll()).thenReturn(ruleList);
		
		List<Rule> newRuleList = ruleService.findAllRule();

		Assertions.assertEquals(ruleList.isEmpty(), newRuleList.isEmpty());
		Assertions.assertEquals(ruleList.size(), newRuleList.size());
		Assertions.assertEquals("Description", newRuleList.get(0).getDescription());
	}
	
	@Test
	public void shouldCreateTest() {
		ruleDto = new RuleFormDTO();
		ruleDto.setName("Name");
		ruleDto.setDescription("Description");
		ruleDto.setJson("JSON");
		ruleDto.setTemplate("Template");
		ruleDto.setSqlStr("SqlStr");
		ruleDto.setSqlPart("SqlPart");
		
		when(ruleRepository.save(any())).thenReturn(new Rule(ruleDto));
		
		Rule savedRule = ruleService.create(ruleDto);
		
		Assertions.assertEquals("Name", savedRule.getName());
		Assertions.assertEquals("Description", savedRule.getDescription());
		Assertions.assertEquals("JSON", savedRule.getJson());
		Assertions.assertEquals("Template", savedRule.getTemplate());
		Assertions.assertEquals("SqlStr", savedRule.getSqlStr());
		Assertions.assertEquals("SqlPart", savedRule.getSqlPart());		
	}
	
	@Test
	public void shouldGetRuleByIdTest() {
		Rule rule = new Rule();
		rule.setId(1);
		rule.setName("Name");
		rule.setDescription("Description");
		rule.setJson("JSON");
		rule.setTemplate("Template");
		rule.setSqlStr("SqlStr");
		rule.setSqlPart("SqlPart");
		
		when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));
		
		RuleFormDTO ruleDto = ruleService.getRuleById(1);
		
		Assertions.assertEquals(1, ruleDto.getId());
		Assertions.assertEquals("Name", ruleDto.getName());
		Assertions.assertEquals("Description", ruleDto.getDescription());
		Assertions.assertEquals("JSON", ruleDto.getJson());
		Assertions.assertEquals("Template", ruleDto.getTemplate());
		Assertions.assertEquals("SqlStr", ruleDto.getSqlStr());
		Assertions.assertEquals("SqlPart", ruleDto.getSqlPart());		
	}
	
	@Test
	public void shouldUpdateTest() {
		ruleDto = new RuleFormDTO();
		ruleDto.setName("Name");
		ruleDto.setDescription("Description");
		ruleDto.setJson("JSON");
		ruleDto.setTemplate("Template");
		ruleDto.setSqlStr("SqlStr");
		ruleDto.setSqlPart("SqlPart");
		
		Rule rule = new Rule();
		rule.setName("First");
		rule.setDescription("Premium");
		rule.setJson("Test");
		rule.setTemplate("Test");
		rule.setSqlStr("Test");
		rule.setSqlPart("Test");
		
		when(ruleRepository.findById(anyInt())).thenReturn(Optional.of(rule));	
		when(ruleRepository.save(any())).thenReturn(new Rule(ruleDto));
		
		Rule updatedRule = ruleService.update(1, ruleDto);
		
		Assertions.assertEquals("Name", updatedRule.getName());
		Assertions.assertEquals("Description", updatedRule.getDescription());
		Assertions.assertEquals("JSON", updatedRule.getJson());
		Assertions.assertEquals("Template", updatedRule.getTemplate());
		Assertions.assertEquals("SqlStr", updatedRule.getSqlStr());
		Assertions.assertEquals("SqlPart", updatedRule.getSqlPart());
	}
	
	@Test
	public void shouldDeleteRuleByIdTest() {
		Rule rule = new Rule();
		rule.setId(1);
		
		ruleService.deleteRuleById(1);
		
		verify(ruleRepository).deleteById(1);
	}
	
	@Test
	public void shouldVerifIsRuleExists() {
		when(ruleRepository.existsById(anyInt())).thenReturn(true);
		
		Assertions.assertTrue(ruleService.ifRuleExists(anyInt()));
	}
}

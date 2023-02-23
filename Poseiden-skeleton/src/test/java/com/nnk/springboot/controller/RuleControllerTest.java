package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.nnk.springboot.controllers.RuleController;
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.dto.RuleFormDTO;
import com.nnk.springboot.services.RuleService;

@AutoConfigureMockMvc
@ContextConfiguration(classes = RuleController.class)
@WebMvcTest
public class RuleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RuleService ruleService;
	private final static String TEST_USER_AUTH = "Admin";
	
	@Test
	public void shouldShowRuleListTest() throws Exception {
		Rule rule = new Rule();
		rule.setId(1);
		rule.setName("TestAccount");
		rule.setDescription("TestType");
		List<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(rule);
		
		when(ruleService.findAllRule()).thenReturn(ruleList);
		
		mockMvc.perform(get("/rule/list")
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("rule/list"))
				.andExpect(model().attribute("ruleList", ruleList))
				.andExpect(content()
						.contentType(MediaType.valueOf("text/html;charset=UTF-8")));
		
		verify(ruleService).findAllRule();
	}
	
	@Test
	public void shouldShowAddRuleFormTest() throws Exception {
		
		mockMvc.perform(get("/rule/add")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", "")
	            .param("description", ""))
        		.andExpect(view().name("rule/add"));
	}
	
	@Test
	public void shouldValidateAddRuleFormTest() throws Exception {
		RuleFormDTO ruleDto = new RuleFormDTO();
		ruleDto.setName("NameTest");
		ruleDto.setDescription("DescriptionTest");
		ruleDto.setJson("JsonTest");
		ruleDto.setTemplate("TemplateTest");
		ruleDto.setSqlStr("StrTest");
		ruleDto.setSqlPart("PartTest");
		Rule newRule = new Rule(ruleDto);

		when(ruleService.create(ruleDto)).thenReturn(newRule);
		
		mockMvc.perform(post("/rule/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", ruleDto.getName())
	            .param("description", ruleDto.getDescription())
	            .param("json", ruleDto.getJson())
	            .param("template", ruleDto.getTemplate())
	            .param("sqlStr", ruleDto.getSqlStr())
	            .param("sqlPart", ruleDto.getSqlPart()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rule/list"));
		
		verify(ruleService, times(1)).create(ruleDto);
	}
	
	@Test
	public void shouldValidateAddRuleEmptyFormTest() throws Exception {
		RuleFormDTO ruleDto = new RuleFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/rule/validate")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", "")
	            .param("description", "")
	            .param("json", "")
	            .param("template", "")
	            .param("sqlStr", "")
	            .param("sqlPart", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("rule/add"));
		
		verify(ruleService, times(0)).create(ruleDto);
	}
	
	@Test
	public void shouldShowUpdateRuleFormTest() throws Exception {
		RuleFormDTO ruleDto = new RuleFormDTO();
		ruleDto.setName("NameTest");
		ruleDto.setDescription("DescriptionTest");
		ruleDto.setJson("JsonTest");
		ruleDto.setTemplate("TemplateTest");
		ruleDto.setSqlStr("StrTest");
		ruleDto.setSqlPart("PartTest");
		
		when(ruleService.ifRuleExists(anyInt())).thenReturn(true);
		when(ruleService.getRuleById(1)).thenReturn(ruleDto);
		
		mockMvc.perform(get("/rule/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", ruleDto.getName())
	            .param("description", ruleDto.getDescription())
	            .param("json", ruleDto.getJson())
	            .param("template", ruleDto.getTemplate())
	            .param("sqlStr", ruleDto.getSqlStr())
	            .param("sqlPart", ruleDto.getSqlPart()))
				.andExpect(status().isOk())
        		.andExpect(view().name("rule/update"))
        		.andExpect(model().attribute("rule", ruleDto));
		
		verify(ruleService).getRuleById(1);
	}
	
	
	@Test
	public void shouldUpdateRuleIfNotExistsTest() throws Exception {
		when(ruleService.ifRuleExists(anyInt())).thenReturn(false);
		
		mockMvc.perform(get("/rule/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH)))
				.andExpect(status().isOk())
				.andExpect(view().name("rule/update"));
		
		verify(ruleService).ifRuleExists(1);
	}
	
	@Test
	public void shouldUpdateRuleTest() throws Exception {
		RuleFormDTO ruleDto = new RuleFormDTO();
		ruleDto.setId(1);
		ruleDto.setName("NameTest");
		ruleDto.setDescription("DescriptionTest");
		ruleDto.setJson("JsonTest");
		ruleDto.setTemplate("TemplateTest");
		ruleDto.setSqlStr("StrTest");
		ruleDto.setSqlPart("PartTest");
		
		when(ruleService.ifRuleExists(anyInt())).thenReturn(true);
		
		mockMvc.perform(post("/rule/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", ruleDto.getName())
	            .param("description", ruleDto.getDescription())
	            .param("json", ruleDto.getJson())
	            .param("template", ruleDto.getTemplate())
	            .param("sqlStr", ruleDto.getSqlStr())
	            .param("sqlPart", ruleDto.getSqlPart()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rule/list"));
		
		verify(ruleService).update(1, ruleDto);
	}
	
	@Test
	public void shouldUpdateInvalidRuleTest() throws Exception {
		RuleFormDTO ruleDto = new RuleFormDTO();
		BindingResult result = mock(BindingResult.class);

		when(result.hasErrors()).thenReturn(true);

		mockMvc.perform(post("/rule/update/1")
				.with(csrf())
				.with(user(TEST_USER_AUTH))
	            .param("name", "")
	            .param("description", "")
	            .param("json", "")
	            .param("template", "")
	            .param("sqlStr", "")
	            .param("sqlPart", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("rule/update"));
		
		verify(ruleService, times(0)).update(1, ruleDto);
	}
	
	@Test
	public void shouldDeleteRuleTest() throws Exception {

		mockMvc.perform( 							
				get("/rule/delete/{id}", 2)
	            .with(csrf())
				.with(user(TEST_USER_AUTH)))
		        .andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rule/list"));
		
		verify(ruleService).deleteRuleById(2);
	}
}

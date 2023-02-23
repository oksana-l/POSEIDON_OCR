package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.dto.RuleFormDTO;

public interface RuleService {

	List<Rule> findAllRule();

	Rule create(RuleFormDTO rule);

	RuleFormDTO getRuleById(Integer id);

	Rule update(Integer id, RuleFormDTO ruleDto);
	
	void deleteRuleById(Integer id);
	
	boolean ifRuleExists(Integer id);
}

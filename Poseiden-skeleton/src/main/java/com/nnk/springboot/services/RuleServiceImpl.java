package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.domain.dto.RuleFormDTO;
import com.nnk.springboot.repositories.RuleRepository;

@Service
public class RuleServiceImpl implements RuleService {
	
	private RuleRepository ruleRepository;

	public RuleServiceImpl(RuleRepository ruleRepository) {
		super();
		this.ruleRepository = ruleRepository;
	}
	
	@Override
	public List<Rule> findAllRule() {
		return ruleRepository.findAll();
	}

	@Override
	public Rule create(RuleFormDTO rule) {
		Rule newRule = new Rule();
		newRule.setName(rule.getName());
		newRule.setDescription(rule.getDescription());
		newRule.setTemplate(rule.getTemplate());
		newRule.setJson(rule.getJson());
		newRule.setSqlStr(rule.getSqlStr());
		newRule.setSqlPart(rule.getSqlPart());
		return ruleRepository.save(newRule);
	}

	@Override
	public RuleFormDTO getRuleById(Integer id) {
		return new RuleFormDTO(ruleRepository.findById(id).get());
	}

	@Override
	public Rule update(Integer id, RuleFormDTO ruleDto) {
		Rule updatedRule = ruleRepository.findById(id).get();
		updatedRule.setName(ruleDto.getName());
		updatedRule.setDescription(ruleDto.getDescription());
		updatedRule.setTemplate(ruleDto.getTemplate());
		updatedRule.setJson(ruleDto.getJson());
		updatedRule.setSqlStr(ruleDto.getSqlStr());
		updatedRule.setSqlPart(ruleDto.getSqlPart());
		return ruleRepository.save(updatedRule);
	}

	@Override
	public void deleteRuleById(Integer id) {
		ruleRepository.deleteById(id);
		
	}
}

package com.nnk.springboot;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;

@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleRepository ruleRepository;

	@Test
	public void ruleTest() {
		Rule rule = new Rule(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleRepository.save(rule);
		Assertions.assertNotNull(rule.getId());
		Assertions.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleRepository.save(rule);
		Assertions.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<Rule> listResult = ruleRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleRepository.delete(rule);
		Optional<Rule> ruleList = ruleRepository.findById(id);
		Assertions.assertFalse(ruleList.isPresent());
	}
}

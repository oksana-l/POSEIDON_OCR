package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.nnk.springboot.domain.dto.RuleFormDTO;

@Entity
@Table(name = "rule")
public class Rule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	Integer id;
	@Column
	@NotBlank(message = "Name is mandatory")
	String name;
	@Column
	@NotBlank(message = "Description is mandatory")
	String description; 
	@Column
	@NotBlank(message = "Json is mandatory")
	String json;
	@Column
	@NotBlank(message = "Template is mandatory")
	String template;
	@Column
	@NotBlank(message = "SQLstr is mandatory")
	String sqlStr; 
	@Column
	@NotBlank(message = "SQLpart is mandatory")
	String sqlPart;
	
	public Rule() {
		
	}

	public Rule(Integer id, String name, String description, String json, 
			String template, String sqlStr, String sqlPart) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}

	public Rule(RuleFormDTO ruleDto) {
		this.id = ruleDto.getId();
		this.name = ruleDto.getName();
		this.description = ruleDto.getDescription();
		this.json = ruleDto.getJson();
		this.template = ruleDto.getTemplate();
		this.sqlStr = ruleDto.getSqlStr();
		this.sqlPart = ruleDto.getSqlPart();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getSqlPart() {
		return sqlPart;
	}

	public void setSqlPart(String sqlPart) {
		this.sqlPart = sqlPart;
	}
	
	
}

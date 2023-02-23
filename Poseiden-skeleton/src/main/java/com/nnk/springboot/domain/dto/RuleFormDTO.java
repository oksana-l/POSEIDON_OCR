package com.nnk.springboot.domain.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.nnk.springboot.domain.Rule;

public class RuleFormDTO {
	
	private Integer id;
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String json;
	@NotBlank
	private String template;
	@NotBlank
	private String sqlStr;
	@NotBlank
	private String sqlPart;
	
	public RuleFormDTO() {
		super();
	}

	public RuleFormDTO(Rule rule) {
		super();
		this.id = rule.getId();
		this.name = rule.getName();
		this.description = rule.getDescription();
		this.json = rule.getJson();
		this.template = rule.getTemplate();
		this.sqlStr = rule.getSqlStr();
		this.sqlPart = rule.getSqlPart();
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

	@Override
	public int hashCode() {
		return Objects.hash(description, id, json, name, sqlPart, sqlStr, template);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleFormDTO other = (RuleFormDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(json, other.json) && Objects.equals(name, other.name)
				&& Objects.equals(sqlPart, other.sqlPart) && Objects.equals(sqlStr, other.sqlStr)
				&& Objects.equals(template, other.template);
	}
	
}

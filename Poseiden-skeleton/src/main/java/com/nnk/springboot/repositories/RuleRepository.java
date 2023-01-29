package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {
}

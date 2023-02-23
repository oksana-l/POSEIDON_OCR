package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

	public Bid getBidById(Integer id);
}

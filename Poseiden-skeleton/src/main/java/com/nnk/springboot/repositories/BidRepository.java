package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

	public Optional<Bid> getBidById (Integer id);
}

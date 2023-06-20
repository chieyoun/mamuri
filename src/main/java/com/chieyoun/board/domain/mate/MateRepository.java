package com.chieyoun.board.domain.mate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateRepository extends JpaRepository<Mate, Integer> {
    List<Mate> findOrdersByUserId(int id);
}
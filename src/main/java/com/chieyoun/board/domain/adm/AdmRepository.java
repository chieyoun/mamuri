package com.chieyoun.board.domain.adm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmRepository extends JpaRepository<Adm, Integer> {
    List<Adm> findAll();
    List<Adm> findSalesById(int id);
    Adm findBySellerId(int id);
}
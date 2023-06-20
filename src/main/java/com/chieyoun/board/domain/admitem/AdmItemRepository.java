package com.chieyoun.board.domain.admitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmItemRepository extends JpaRepository<AdmItem, Integer> {
    List<AdmItem> findSaleItemsBySellerId(int sellerId);
    List<AdmItem> findAll();
}

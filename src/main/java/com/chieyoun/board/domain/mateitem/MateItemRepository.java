package com.chieyoun.board.domain.mateitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateItemRepository extends JpaRepository<MateItem, Integer> {
    List<MateItem> findOrderItemsByUserId(int userId);
    List<MateItem> findAll();
    MateItem findOrderItemById(int orderItemId);
}

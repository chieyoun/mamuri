package com.chieyoun.board.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findItemById(int id);
    Page<Item> findByNameContaining(String searchKeyword, Pageable pageable);
}

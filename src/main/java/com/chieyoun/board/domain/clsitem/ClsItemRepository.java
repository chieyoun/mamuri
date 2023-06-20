package com.chieyoun.board.domain.clsitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClsItemRepository extends JpaRepository<ClsItem, Integer> {
    ClsItem findByCartIdAndItemId(int cartId, int itemId);
    ClsItem findCartItemById(int id);
    List<ClsItem> findCartItemByItemId(int id);
}

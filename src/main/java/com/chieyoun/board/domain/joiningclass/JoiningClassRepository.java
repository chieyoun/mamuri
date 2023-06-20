package com.chieyoun.board.domain.joiningclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoiningClassRepository extends JpaRepository<JoiningClass, Integer> {
    JoiningClass findByUserId(int id);
    JoiningClass findCartById(int id);
    JoiningClass findCartByUserId(int id);
}

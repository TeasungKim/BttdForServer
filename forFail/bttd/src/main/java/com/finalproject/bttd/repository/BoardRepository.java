package com.finalproject.bttd.repository;

import com.finalproject.bttd.dto.BoardDto;
import com.finalproject.bttd.dto.Boards;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Role;
import com.finalproject.bttd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Override
    ArrayList<Board> findAll();

    @Query("SELECT new Board(b.post_id, b.post_context, b.post_title) FROM Board b")
    List<Boards> findAllm();

}


package com.finalproject.bttd.service;

import com.finalproject.bttd.dto.BoardDto;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
   public Board boardwrite(BoardDto boardDto){
       Board board = boardDto.toEntity();
       log.info("boardService : " + board);
       return boardRepository.save(board);
   }




    //
}

package com.finalproject.bttd.service;

import com.finalproject.bttd.dto.BoardDto;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.mybatismapper.BoardBatisMapper;
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
    @Autowired
    private BoardBatisMapper boardBatisMapper;

   public Board boardwrite(BoardDto boardDto){
       Board board = boardDto.toEntity();
       log.info("boardService : " + board);
       return boardRepository.save(board);
   }
   public Board accessButton(int post_id){

       Optional<Board> boards = boardRepository.findById(post_id);

       log.info("BaordService Board : " + boards );

    if(boards.isPresent()){
        Board boardE = boards.get();
        boardE.setUser_confirm(true);
        boardE.setAway_id(boardE.getAway_id());
        boardRepository.save(boardE);
        log.info("BoardService saved : " + boardE);
        return boardE;
    }
    return null;
   }




    //
}

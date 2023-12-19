package com.finalproject.bttd.mapper;

import com.finalproject.bttd.dto.Boards;
import com.finalproject.bttd.entity.Board;

public class BoardMapper {

    public static Boards toDto(Board board) {
        Boards dto = new Boards();
        dto.setPost_id(board.getPost_id());
        dto.setPost_context(board.getPost_context());
        dto.setPost_title(board.getPost_title());
        // 다른 필드들도 마찬가지로 설정
        return dto;
    }
}

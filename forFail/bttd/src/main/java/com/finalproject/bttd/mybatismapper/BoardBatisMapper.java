package com.finalproject.bttd.mybatismapper;

import com.finalproject.bttd.dto.Comments;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardBatisMapper {

    List<Board> getWithComment();
    List<Board> getUserBoard(String user_id);

    List<Board> findByTitleContaining(String keyword);

    List<Comments> findByPostUser(int postId);



}

package com.finalproject.bttd.mybatismapper;

import com.finalproject.bttd.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardBatisMapper {

    List<Board> getWithComment();


}

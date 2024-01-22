package com.finalproject.bttd.dto;


import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comments {

    public int post_id;
    public String post_title;
    public String post_context;
    public Boolean score;
    public String user_id;
    public String away_id;


    public String user_age;
    public String user_weight;
    public int user_win;
    public int user_lose;



    List<CommentDetails> comments;



}

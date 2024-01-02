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
  List<CommentDetails> comments;



}

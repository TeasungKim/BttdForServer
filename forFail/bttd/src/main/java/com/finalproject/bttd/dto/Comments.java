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
   private Long request_form_id;
   private String request_user_id;
   private String request_context;
   private Date request_date;


}

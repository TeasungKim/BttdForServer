package com.finalproject.bttd.dto;


import com.finalproject.bttd.entity.Board;
import lombok.Data;

@Data
public class Comments {
   public int post_id;
   public String request_user_id;
   public String request_context;


}

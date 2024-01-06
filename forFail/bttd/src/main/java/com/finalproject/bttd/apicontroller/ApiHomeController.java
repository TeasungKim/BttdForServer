package com.finalproject.bttd.apicontroller;

import com.finalproject.bttd.apiresponse.ApiResponse;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.mybatismapper.BoardBatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.finalproject.bttd.apiresponse.ApiResponse.SUCCESS_STATUS;

@RestController
public class ApiHomeController {
    @Autowired
    private BoardBatisMapper boardBatisMapper;
    @GetMapping("/api/searchBoard")
    public ResponseEntity<ApiResponse<List<Board>>> searchBoard(@RequestParam String keyword){
        List<Board> boards = boardBatisMapper.findByTitleContaining(keyword);

        ApiResponse<List<Board>> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("Success");
        response.setData(boards);

        return ResponseEntity.ok(response);
    }

}

package com.example.board2.controller;

import com.example.board2.dto.ResponseDto;
import com.example.board2.entity.BoardEntity;
import com.example.board2.entity.PopularSearchEntity;
import com.example.board2.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String getBoard(@AuthenticationPrincipal String userEmail) {
        //AuthenticationPrincipal : JwtAuthenticationFilter 에서 userEmail을 SecurityContext 에 담았고, 그걸 사용할때 쓰는 어노테이션
        return "로그인된 사용자는" + userEmail + "입니다.";
    }

    @GetMapping("/top3")
    public ResponseDto<List<BoardEntity>> getTop3(){

        return boardService.getTop3();
    }

    @GetMapping("/list")
    public ResponseDto<List<BoardEntity>> getList(){
        return boardService.getList();
    }

    @GetMapping("/popularsearchList")
     public ResponseDto<List<PopularSearchEntity>> getPopularsearchList(){
        return boardService.getPopularsearchList();
    }

    @GetMapping("/search/{boardTitle}")
    public ResponseDto<List<BoardEntity>> getSearchList(@PathVariable("boardTitle") String title){
        return null;
    }
}
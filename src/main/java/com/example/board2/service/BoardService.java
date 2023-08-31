package com.example.board2.service;

import com.example.board2.dto.ResponseDto;
import com.example.board2.entity.BoardEntity;
import com.example.board2.entity.PopularSearchEntity;
import com.example.board2.repository.BoardRepository;
import com.example.board2.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    PopularSearchRepository popularSearchRepository;

    public ResponseDto<List<BoardEntity>> getTop3(){
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        Date date = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));

        try {
            boardList = boardRepository.findTop3ByBoardWriteDateAfterOrderByBoardLikesCountDesc(date);
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<BoardEntity>> getList() {
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();

        try {
            boardList = boardRepository.findByOrderByBoardWriteDateDesc();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }

    public ResponseDto<List<PopularSearchEntity>> getPopularsearchList(){
        List<PopularSearchEntity> popularsearchListst = new ArrayList<PopularSearchEntity>();
        try {
            popularsearchListst = popularSearchRepository.findTop10ByOrderByPopularSearchCountDesc();

        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", popularsearchListst);
    }

    public ResponseDto<List<BoardEntity>> getSearchList(String boardTitle) {
        List<BoardEntity> boardList = new ArrayList<BoardEntity>();
        try {
            boardList = boardRepository.findByBoardTitleContains(boardTitle);
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.setFailed("Database Error");
        }
        return ResponseDto.setSuccess("Success", boardList);
    }


}

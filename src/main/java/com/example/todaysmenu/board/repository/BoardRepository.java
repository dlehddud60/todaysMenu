package com.example.todaysmenu.board.repository;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.pagination.DTO.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    int count(Criteria cri);
    List<BoardDTO> list();
    List<BoardDTO> listPaging(Criteria cri);
    BoardDTO info(int tfb_seq);
    int insert(BoardDTO freeboarddto);
    void update(BoardDTO freeboarddto);
    void delete(int tfb_seq);
}

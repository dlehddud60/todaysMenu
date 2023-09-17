package com.example.todaysmenu.board.repository;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.entity.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    public int count(Criteria cri);
    public List<BoardDTO> list();
    public List<BoardDTO> listPaging(Criteria cri);
    public BoardDTO info(int tfb_seq);
    public void insert(BoardDTO freeboarddto);
    public void update(BoardDTO freeboarddto);
    public void delete(int tfb_seq);
}

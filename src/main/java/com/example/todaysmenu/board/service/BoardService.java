package com.example.todaysmenu.board.service;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.entity.Criteria;

import java.util.List;

public interface BoardService {
//    public List<BoardDTO> list();
    public int count(Criteria cri);
    public List<BoardDTO> list(Criteria cri);
    public BoardDTO info(int tfb_seq);
    public void insert(BoardDTO freeboarddto);
    public void update(BoardDTO freeboarddto);
    public void delete(int tfb_seq);
}

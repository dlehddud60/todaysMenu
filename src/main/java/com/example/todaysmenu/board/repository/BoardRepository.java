package com.example.todaysmenu.board.repository;

import com.example.todaysmenu.board.DTO.BoardDTO;
import com.example.todaysmenu.board.model.FindResponseBoardInfoModel;
import com.example.todaysmenu.board.model.FindResponseBoardListModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    int count(Criteria cri);
    List<FindResponseBoardListModel> list();
    List<FindResponseBoardListModel> listPaging(Criteria cri);
    FindResponseBoardInfoModel info(int tfb_seq);
    int insert(BoardDTO freeboarddto);
    void update(BoardDTO freeboarddto);
    void delete(int tfb_seq);
}

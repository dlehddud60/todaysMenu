package com.example.todaysmenu.board.service;

import com.example.todaysmenu.board.DTO.BoardDTO;
import com.example.todaysmenu.board.model.FindResponseBoardInfoModel;
import com.example.todaysmenu.board.model.FindResponseBoardListModel;
import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.pagination.VO.Criteria;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoardService {
//    public List<RestaurantDTO> list();
    int count(Criteria cri);
    List<FindResponseBoardListModel> list(Criteria cri);
    FindResponseBoardInfoModel info(int tfb_seq);
    void insert(BoardDTO freeboarddto);
    void insert(BoardDTO boardDTO, BoardFileDTO boardFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;
    void update(BoardDTO boardDTO);

    void update(BoardDTO boardDTO, BoardFileDTO boardFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;
    void delete(int tfb_seq);
    void delete(int tfb_seq, BoardFileDTO boardFileDTO);
}

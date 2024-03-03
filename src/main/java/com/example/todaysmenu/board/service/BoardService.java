package com.example.todaysmenu.board.service;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BoardService {
//    public List<RestaurantDTO> list();
    int count(Criteria cri);
    List<BoardDTO> list(Criteria cri);
    BoardDTO info(int tfb_seq);
    void insert(BoardDTO freeboarddto);
    void insert(BoardDTO boardDTO, CommonFileDTO commonFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;
    void update(BoardDTO boardDTO);

    void update(BoardDTO boardDTO, CommonFileDTO commonFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;
    void delete(int tfb_seq);
    void delete(int tfb_seq, CommonFileDTO commonFileDTO);
}

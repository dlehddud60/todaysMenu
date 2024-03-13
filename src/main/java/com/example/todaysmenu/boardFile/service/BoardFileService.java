package com.example.todaysmenu.boardFile.service;

import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;

import java.util.List;

public interface BoardFileService {
    List<BoardFileDTO> list(BoardFileDTO boardFileDTO);
    int insert(BoardFileDTO boardFileDTO);
}

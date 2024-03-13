package com.example.todaysmenu.boardFile.service.impl;

import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import com.example.todaysmenu.boardFile.repository.BoardFileRepository;
import com.example.todaysmenu.boardFile.service.BoardFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {

   private final BoardFileRepository boardFileRepository;

    @Override
    public List<BoardFileDTO> list(BoardFileDTO boardFileDTO) {
        return boardFileRepository.list(boardFileDTO);
    }

    @Override
    public int insert(BoardFileDTO boardFileDTO) {
        return boardFileRepository.insert(boardFileDTO);
    }

}


package com.example.todaysmenu.board.service.impl;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardDTO> list() {
        log.info("list 호출===========");
        return boardRepository.list();
    }

    @Override
    public BoardDTO info(int tfb_seq) {
        log.info("info 호출===========");
        return boardRepository.info(tfb_seq);
    }

    @Override
    public void insert(BoardDTO freeboarddto) {
        log.info("insert 호출===========");
      boardRepository.insert(freeboarddto);
    }

    @Override
    public void update(BoardDTO freeboarddto) {

    }

    @Override
    public void delete(int tfb_seq) {

    }


}

package com.example.todaysmenu.board.service.impl;

import com.example.todaysmenu.board.DTO.BoardDTO;
import com.example.todaysmenu.board.model.FindResponseBoardInfoModel;
import com.example.todaysmenu.board.model.FindResponseBoardListModel;
import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import com.example.todaysmenu.boardFile.repository.BoardFileRepository;
import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.todaysmenu.boardFile.util.BoardUtil.boardFileDelMethod;
import static com.example.todaysmenu.boardFile.util.BoardUtil.boardFileMethod;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

   private final BoardRepository boardRepository;
   private final BoardFileRepository boardFileRepository;

    @Override
    public int count(Criteria cri) {
        return boardRepository.count(cri);
    }



    @Override
    public List<FindResponseBoardListModel> list(Criteria cri) {
        return boardRepository.listPaging(cri);
    }



    @Override
    public FindResponseBoardInfoModel info(int tfb_seq) {
        return boardRepository.info(tfb_seq);
    }

    @Override
    public void insert(BoardDTO freeboarddto) {
//      boardRepository.insert(freeboarddto);
    }
    @Override
    public void insert(BoardDTO freeboarddto, BoardFileDTO boardFileDTO, HttpServletRequest request)throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = boardFileDTO.getTcft_multi_file().get(0).isEmpty();
        boardRepository.insert(freeboarddto);
        if(!fileResult) {
            boardFileMethod(freeboarddto, boardFileDTO, boardFileRepository, request);
        }
    }

    @Override
    public void update(BoardDTO boardDTO, BoardFileDTO boardFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = boardFileDTO.getTcft_multi_file().get(0).isEmpty();
        boardRepository.update(boardDTO);
        if(!fileResult) {
            boardFileMethod(boardDTO, boardFileDTO, boardFileRepository, request);
        }
    }

    @Override
    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }


    @Override
    public void delete(int tfb_seq) {
        boardRepository.delete(tfb_seq);
    }

    @Override
    public void delete(int tfb_seq, BoardFileDTO boardFileDTO) {

        boardFileDelMethod(boardFileDTO);
        boardRepository.delete(tfb_seq);
    }


}

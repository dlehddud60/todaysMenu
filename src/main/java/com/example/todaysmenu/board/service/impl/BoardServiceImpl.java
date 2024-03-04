package com.example.todaysmenu.board.service.impl;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.repository.CommonFileRepository;
import com.example.todaysmenu.common.commonFile.util.CommonFile;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.memFile.entity.MemFileDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.todaysmenu.common.commonFile.util.CommonFile.*;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommonFileRepository commonFileRepository;


    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public int count(Criteria cri) {
        return boardRepository.count(cri);
    }



    @Override
    public List<BoardDTO> list(Criteria cri) {
        return boardRepository.listPaging(cri);
    }



    @Override
    public BoardDTO info(int tfb_seq) {
        return boardRepository.info(tfb_seq);
    }

    @Override
    public void insert(BoardDTO freeboarddto) {
//      boardRepository.insert(freeboarddto);
    }
    @Override
    public void insert(BoardDTO freeboarddto, CommonFileDTO commonFileDTO, HttpServletRequest request)throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = commonFileDTO.getTcft_multi_file().get(0).isEmpty();
        boardRepository.insert(freeboarddto);
        if(!fileResult) {
            commonFileMethod(freeboarddto, commonFileDTO,commonFileRepository, request);
        }
    }

    @Override
    public void update(BoardDTO boardDTO, CommonFileDTO commonFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = commonFileDTO.getTcft_multi_file().get(0).isEmpty();
        boardRepository.update(boardDTO);
        if(!fileResult) {
            commonFileMethod(boardDTO, commonFileDTO,commonFileRepository, request);
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
    public void delete(int tfb_seq, CommonFileDTO commonFileDTO) {

        log.info("============Service commonFileDTO==============={}",commonFileDTO);
        String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";

        List<String> commonFileDTOArr = commonFileDTO.getTcft_change_fine_nameArr();
        for (int i = 0; i < commonFileDTOArr.size(); i++) {
            String oldFileName = commonFileDTOArr.get(i);
            log.info("==============countOldFileName============{}",i);
            log.info("====================oldFileName==================={}",oldFileName);

            File oldFile = new File(savePath + "/" + oldFileName);
            if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                log.info("====================exists==================={}",oldFile.exists());

                oldFile.delete();
            }
        }
        boardRepository.delete(tfb_seq);
    }
}

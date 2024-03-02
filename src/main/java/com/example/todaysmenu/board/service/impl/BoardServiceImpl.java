package com.example.todaysmenu.board.service.impl;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.repository.CommonFileRepository;
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

        log.info("===============commonFileDTO1================={}",commonFileDTO);

        log.info("----------------------freeboarddto1------------------------{}",freeboarddto);
        int resut = boardRepository.insert(freeboarddto);
        log.info("----------------------freeboarddto2------------------------{}",freeboarddto);
        log.info("----------------------resut------------------------{}",resut);


        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");


        int dataSeq = commonFileDTO.getTcft_seq();


        // 파일만 따로 가져오기
        List<MultipartFile> comFileList = commonFileDTO.getTcft_multi_file();
        for (int i = 0; i < comFileList.size(); i++) {

            long memFileSize = comFileList.get(i).getSize();
            int fileSize = 3145728;

            // 파일 이름 가져오기
            String originalFilename = comFileList.get(i).getOriginalFilename();

            // 저장용 이름 만들기
            System.out.println(System.currentTimeMillis());
            String storedFileName = System.currentTimeMillis() + "-" + originalFilename;

            // FileDTO 세팅
            commonFileDTO.setTcft_origin_file_name(originalFilename);
            commonFileDTO.setTcft_change_fine_name(storedFileName);
            commonFileDTO.setTcft_parent_table_type("TM_FREE_BOARD");

            commonFileDTO.setTcft_input_ty(memberSession.getTmt_user_type());
            commonFileDTO.setTcft_input_nm(memberSession.getTmt_memb_name());
            commonFileDTO.setTcft_input_id(memberSession.getTmt_login_id());
            commonFileDTO.setTcft_input_ip(request.getRemoteAddr());
            commonFileDTO.setTcft_parent_seq(freeboarddto.getTfb_seq());

            log.info("===============commonFileDTO================={}",commonFileDTO);

            // 파일 저장용 폴더에 파일 저장 처리
            String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";
            //새로 업로드된 이미지(new 1.png), 현재 DB에 있는 이미지(old 4.png)
            CommonFileDTO dataSeqDTO = new CommonFileDTO();
            dataSeqDTO.setTcft_seq(dataSeq);

            if (comFileList != null) {//업로드가 된 상태(png. jpg, gif)
                //이미지 파일 여부를 체크 -> 만약 이미지 파일이 아니면 삭제
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                ext = ext.toUpperCase();
                if (ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")|| ext.equals("JPEG")) {
                    if(memFileSize > fileSize) {
                        throw new FileSizeExaption("이미지 첨부 용량은 3MB를 넘을 수 없습니다");
                    }
                    if (dataSeq > 0) {
                        String oldFileName = commonFileRepository.list(dataSeqDTO).get(i).getTcft_change_fine_name();
                        log.info("=========oldFileName========={}", oldFileName);
                        File oldFile = new File(savePath + "/" + oldFileName);
                        if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                            oldFile.delete();
                        }
                    }
                }else {
                    throw new FileExtensionExaption("이미지만 첨부 가능하며 PNG,GIF,JPG,JPEG의 확장자만 첨부 가능합니다.");
                }
            }


            try {
                comFileList.get(i).transferTo(new File(savePath+storedFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // board_file_table 저장 처리
            log.info("==============dataSeq============={}",dataSeq);
            if(dataSeq == 0) {
                commonFileRepository.insert(commonFileDTO);
           }
//            else {
//                log.info("==============update============={}",dataSeq);
//                commonFileDTO.setTcft_moder_ty(memberSession.getTmt_user_type());
//                commonFileDTO.setTcft_moder_nm(memberSession.getTmt_memb_name());
//                commonFileDTO.setTcft_moder_id(memberSession.getTmt_login_id());
//                commonFileDTO.setTcft_moder_ip(request.getRemoteAddr());
//                commonFileRepository.update(commonFileDTO);
//
//            }

            log.info("----------------------resut------------------------{}",resut);

        }




    }
    @Override
    public void update(BoardDTO freeboarddto) {
        boardRepository.update(freeboarddto);
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

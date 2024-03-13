package com.example.todaysmenu.boardFile.util;

import com.example.todaysmenu.board.DTO.BoardDTO;
import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import com.example.todaysmenu.boardFile.repository.BoardFileRepository;
import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.member.DTO.MemberDTO;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
public abstract class BoardUtil {


    public static void boardFileMethod(BoardDTO boardDTO, BoardFileDTO boardFileDTO, BoardFileRepository boardFileRepository, HttpServletRequest request) throws FileSizeExaption, FileExtensionExaption {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";
        int tfb_seq = boardDTO.getTfb_seq();
        if(tfb_seq > 0) {
           ; boardFileDTO.setTcft_parent_seq(tfb_seq);
            boardFileDTO.setTcft_moder_ty(memberSession.tmt_user_type());
            boardFileDTO.setTcft_moder_nm(memberSession.tmt_memb_name());
            boardFileDTO.setTcft_moder_id(memberSession.tmt_login_id());
            boardFileDTO.setTcft_moder_ip(request.getRemoteAddr());
            List<BoardFileDTO> fileList = boardFileRepository.list(boardFileDTO);
            for (int i = 0; i < fileList.size(); i++) {
                String oldFileName = fileList.get(i).getTcft_change_fine_name();
                File oldFile = new File(savePath + "/" + oldFileName);
                    if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                        oldFile.delete();
                    }
            }
            boardFileRepository.delete(tfb_seq);
        }
        int dataSeq = boardFileDTO.getTcft_seq();

        // 파일만 따로 가져오기
        List<MultipartFile> comFileList = boardFileDTO.getTcft_multi_file();
        for (int i = 0; i < comFileList.size(); i++) {

            long memFileSize = comFileList.get(i).getSize();
            int fileSize = 3145728;

            // 파일 이름 가져오기
            String originalFilename = comFileList.get(i).getOriginalFilename();

            // 저장용 이름 만들기
            System.out.println(System.currentTimeMillis());
            String storedFileName = System.currentTimeMillis() + "-" + originalFilename;

            // FileDTO 세팅
            boardFileDTO.setTcft_origin_file_name(originalFilename);
            boardFileDTO.setTcft_change_fine_name(storedFileName);
            boardFileDTO.setTcft_parent_table_type("TM_FREE_BOARD");

            boardFileDTO.setTcft_input_ty(memberSession.tmt_user_type());
            boardFileDTO.setTcft_input_nm(memberSession.tmt_memb_name());
            boardFileDTO.setTcft_input_id(memberSession.tmt_login_id());
            boardFileDTO.setTcft_input_ip(request.getRemoteAddr());
            boardFileDTO.setTcft_parent_seq(boardDTO.getTfb_seq());

            // 파일 저장용 폴더에 파일 저장 처리
            //새로 업로드된 이미지(new 1.png), 현재 DB에 있는 이미지(old 4.png)
            if (comFileList != null) {//업로드가 된 상태(png. jpg, gif)
                //이미지 파일 여부를 체크 -> 만약 이미지 파일이 아니면 삭제
                String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                ext = ext.toUpperCase();
                if (ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")|| ext.equals("JPEG")) {
                    if(memFileSize > fileSize) {
                        throw new FileSizeExaption("이미지 첨부 용량은 3MB를 넘을 수 없습니다");
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
            boardFileRepository.insert(boardFileDTO);
        }
    }



    public static void boardFileDelMethod(BoardFileDTO boardFileDTO) {
        log.info("=============commonFileDelMethod 호출==============");
        String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";
        List<String> commonFileDTOArr = boardFileDTO.getTcft_change_fine_nameArr();
        for (int i = 0; i < commonFileDTOArr.size(); i++) {
            String oldFileName = commonFileDTOArr.get(i);
            File oldFile = new File(savePath + "/" + oldFileName);
            if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                oldFile.delete();
            }
        }
    }


}

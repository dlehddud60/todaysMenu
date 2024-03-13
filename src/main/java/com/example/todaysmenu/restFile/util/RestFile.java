package com.example.todaysmenu.restFile.util;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.restFile.entity.RestFileDTO;
import com.example.todaysmenu.restFile.repository.RestFileRepository;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log4j2
public abstract class RestFile {


    public static void restFileMethod(RestaurantDTO restaurantDTO, RestFileDTO restFileDTO, RestFileRepository restFileRepository, HttpServletRequest request) throws FileSizeExaption, FileExtensionExaption {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";
        int trt_seq = restaurantDTO.getTrt_seq();
        if(trt_seq > 0) {
           ; restFileDTO.setTrft_parent_seq(trt_seq);
            restFileDTO.setTrft_moder_ty(memberSession.getTmt_user_type());
            restFileDTO.setTrft_moder_nm(memberSession.getTmt_memb_name());
            restFileDTO.setTrft_moder_id(memberSession.getTmt_login_id());
            restFileDTO.setTrft_moder_ip(request.getRemoteAddr());
            List<RestFileDTO> fileList = restFileRepository.list(restFileDTO);
            for (int i = 0; i < fileList.size(); i++) {
                String oldFileName = fileList.get(i).getTrft_change_file_name();
                File oldFile = new File(savePath + "/" + oldFileName);
                    if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                        oldFile.delete();
                    }
            }
            restFileRepository.delete(trt_seq);
        }
        int dataSeq = restFileDTO.getTrft_seq();

        // 파일만 따로 가져오기
        List<MultipartFile> restFileList = restFileDTO.getTrft_multi_file();
        for (int i = 0; i < restFileList.size(); i++) {

            long memFileSize = restFileList.get(i).getSize();
            int fileSize = 3145728;

            // 파일 이름 가져오기
            String originalFilename = restFileList.get(i).getOriginalFilename();

            // 저장용 이름 만들기
            System.out.println(System.currentTimeMillis());
            String storedFileName = System.currentTimeMillis() + "-" + originalFilename;

            // FileDTO 세팅
            restFileDTO.setTrft_origin_file_name(originalFilename);
            restFileDTO.setTrft_change_file_name(storedFileName);
            restFileDTO.setTrft_parent_table_type("TM_REST_TBL");

            restFileDTO.setTrft_input_ty(memberSession.getTmt_user_type());
            restFileDTO.setTrft_input_nm(memberSession.getTmt_memb_name());
            restFileDTO.setTrft_input_id(memberSession.getTmt_login_id());
            restFileDTO.setTrft_input_ip(request.getRemoteAddr());
            restFileDTO.setTrft_parent_seq(restaurantDTO.getTrt_seq());

            // 파일 저장용 폴더에 파일 저장 처리
            //새로 업로드된 이미지(new 1.png), 현재 DB에 있는 이미지(old 4.png)
            if (restFileList != null) {//업로드가 된 상태(png. jpg, gif)
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
                restFileList.get(i).transferTo(new File(savePath+storedFileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // board_file_table 저장 처리
            restFileRepository.insert(restFileDTO);
        }
    }



    public static void restFileDelMethod(RestFileDTO restFileDTO) {
        log.info("=============commonFileDelMethod 호출==============");
        String savePath = "C:/project/todaysMenu/src/main/resources/static/upload/";
        List<String> commonFileDTOArr = restFileDTO.getTrft_change_file_nameArr();
        for (int i = 0; i < commonFileDTOArr.size(); i++) {
            String oldFileName = commonFileDTOArr.get(i);
            File oldFile = new File(savePath + "/" + oldFileName);
            if (oldFile.exists()) { //upload경로에 파일이 존재하는지 확인하는 메서드
                oldFile.delete();
            }
        }
    }


}

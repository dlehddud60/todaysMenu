package com.example.todaysmenu.common;

import com.example.todaysmenu.member.DTO.MemberDTO;
import com.example.todaysmenu.memFile.DTO.MemFileDTO;
import com.example.todaysmenu.memFile.repository.MemFileRepository;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class CommonGlobalController {

   private final MemFileRepository fileRepository;

    @ModelAttribute
    public void globalDataBind(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        String userIp = request.getRemoteAddr();
        log.info("USER_IP{}",userIp);
        try{
            int memberSeq = Integer.parseInt(memberSession.tmt_seq());
            MemFileDTO memFileDTO = new MemFileDTO(memberSeq);
            model.addAttribute("memFile",fileRepository.list(memFileDTO));
        }catch (NullPointerException e) {
            log.info("NullPointerException");
            log.info("비로그인 유저 접속");
        }
    }
}

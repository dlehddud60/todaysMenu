package com.example.todaysmenu.common;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.memFile.entity.MemFileDTO;
import com.example.todaysmenu.member.memFile.repository.MemFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@Log4j2
@ControllerAdvice
public class CommonGlobalController {

    @Autowired
    MemFileRepository fileRepository;

    @ModelAttribute
    public void globalDataBind(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        String userIp = request.getRemoteAddr();
        log.info("USER_IP{}",userIp);
        try{
            int memberSeq = Integer.parseInt(memberSession.getTmt_seq());
            MemFileDTO memFileDTO = new MemFileDTO(memberSeq);
            model.addAttribute("memFile",fileRepository.list(memFileDTO));
        }catch (NullPointerException e) {
            log.info("NullPointerException");
            log.info("비로그인 유저 접속");
        }
    }
}

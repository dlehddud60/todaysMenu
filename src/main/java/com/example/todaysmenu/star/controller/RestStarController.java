package com.example.todaysmenu.star.controller;

import com.example.todaysmenu.member.DTO.MemberDTO;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import com.example.todaysmenu.star.DTO.RestStarDTO;
import com.example.todaysmenu.star.service.RestStarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Controller
@RequestMapping("/star/*")
@RequiredArgsConstructor
public class RestStarController {
    private final RestStarService restStarService;

    @PostMapping("/proc.do")
     public @ResponseBody  int  StarInsert(@ModelAttribute RestStarDTO restStarDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");

        int trst_seq = restStarDTO.getTrst_seq();
        int result;
        if(trst_seq < 1) {
            log.info("=====insert======{}",restStarDTO);
            restStarDTO.setTrst_input_ty(memberSession.tmt_user_type());
            restStarDTO.setTrst_input_nm(memberSession.tmt_memb_name());
            restStarDTO.setTrst_input_id(memberSession.tmt_login_id());
            restStarDTO.setTrst_input_ip(request.getRemoteAddr());
             result = restStarService.insert(restStarDTO);
        }else {
            restStarDTO.setTrst_moder_ty(memberSession.tmt_user_type());
            restStarDTO.setTrst_moder_nm(memberSession.tmt_memb_name());
            restStarDTO.setTrst_moder_id(memberSession.tmt_login_id());
            restStarDTO.setTrst_moder_ip(request.getRemoteAddr());
            log.info("=====update======{}",restStarDTO);
            result = restStarService.update(restStarDTO);
        }

        return result;
    }









}

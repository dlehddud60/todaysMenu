package com.example.todaysmenu.restaurant.star.controller;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.star.entity.RestStarDTO;
import com.example.todaysmenu.restaurant.star.service.RestStarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Log4j2
@Controller
@RequestMapping("/star/*")
public class RestStarController {
    @Autowired
    RestStarService restStarService;

    @PostMapping("/proc.do")

    public @ResponseBody  int  StarInsert(@ModelAttribute RestStarDTO restStarDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");

        int trst_seq = restStarDTO.getTrst_seq();
        int result;
        if(trst_seq < 1) {
            log.info("=====insert======{}",restStarDTO);
            restStarDTO.setTrst_input_ty(memberSession.getTmt_user_type());
            restStarDTO.setTrst_input_nm(memberSession.getTmt_memb_name());
            restStarDTO.setTrst_input_id(memberSession.getTmt_login_id());
            restStarDTO.setTrst_input_ip(request.getRemoteAddr());
             result = restStarService.insert(restStarDTO);
        }else {
            restStarDTO.setTrst_moder_ty(memberSession.getTmt_user_type());
            restStarDTO.setTrst_moder_nm(memberSession.getTmt_memb_name());
            restStarDTO.setTrst_moder_id(memberSession.getTmt_login_id());
            restStarDTO.setTrst_moder_ip(request.getRemoteAddr());
            log.info("=====update======{}",restStarDTO);
            result = restStarService.update(restStarDTO);
        }

        return result;
    }
}
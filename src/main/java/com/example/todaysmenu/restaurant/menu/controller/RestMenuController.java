package com.example.todaysmenu.restaurant.menu.controller;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.pagination.entity.PageDTO;
import com.example.todaysmenu.restaurant.menu.service.impl.RestMenuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.todaysmenu.board.common.modal.ComModal.DANGER;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;


@Log4j2
@Controller
@RequestMapping("/restMenu/*")
public class RestMenuController {



    @Autowired
    RestMenuServiceImpl restMenuService;


    @GetMapping("/index.do")
    public String list(Criteria cri, Model model, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        try{
            cri.setTmt_login_id(memberSession.getTmt_login_id());
        }catch (NullPointerException e) {
            return redirect("",rttr,"실패 메세지","비로그인 유저는 진입하실 수 없습니다.",DANGER);
        }
        log.info("=======list 호출======{}",cri);
        int total = restMenuService.count(cri);
        model.addAttribute("list", restMenuService.listPaging(cri));
        model.addAttribute("pageMaker",new PageDTO(total,cri));
        return "restaurant/restMenu/list";
    }


}

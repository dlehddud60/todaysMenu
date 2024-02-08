package com.example.todaysmenu.member.controller;

import com.example.todaysmenu.board.common.modal.ComModal;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.member.service.MemberService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;

@Controller
@Log4j2
public class MemberController {

    String danger = "btn btn-danger";
    String success = "btn btn-success";

    ComModal comModal = new ComModal();

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;



    @RequestMapping(value = {"/join.do","/updateLogin.do"})
    public String join() {
        log.info("회원가입 접속");
        return "member/join";
    }
    @RequestMapping("/registerCheck.do")
    public @ResponseBody int registerCheck(@RequestParam("tmt_login_id") String tmt_login_id) {
        log.info("-------registerCheckMemberController-------");
        return memberService.registerCheck(tmt_login_id);
    }
    @RequestMapping("/register.do")
    public String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2,
                           RedirectAttributes rttr, HttpSession session, @RequestParam(value = "tmt_seq",required = false)String tmt_seq) {
        String register = memberService.register(memberDTO,tmt_pass_word1,tmt_pass_word2,rttr,session,tmt_seq);

      return register;
    }

    @RequestMapping("/logout.do")
    public String logout(RedirectAttributes rttr,HttpSession session) {

        session.invalidate();
        rttr.addFlashAttribute("msgType","로그아웃 메시지");
        rttr.addFlashAttribute("msg","로그아웃을 하였습니다.");
        rttr.addFlashAttribute("result",danger);
        return "redirect:/";
    }
    @RequestMapping("loginForm.do")
    public String loginForm() {
        log.info("로그인");
        return "member/login";
    }
    @RequestMapping("login.do")
    public String login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session) {

        String login = memberService.login(memberDTO,rttr,session);
        return login;
    }

    @RequestMapping("/imageForm.do")
    public String imageForm() {
        return "member/imageForm";
    }


//    @RequestMapping("/imageUpdate.do")
//    public String imageUpdate(HttpServletRequest request, RedirectAttributes rttr) {
//        MultipartRequest multi = null;
//        int fileMaxSize = 10*1024*1024;
//        String savePath = request.getServletContext().getRealPath("resources/upload");
//        try {
//            multi = new MultipartRequest(request,savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
//        } catch (Exception e) {
//            e.printStackTrace();
//            comModal.redirect("",rttr,"실패 메세지","파일의 크기는 10MB를 넘을 수 없습니다.",danger);
//
//        }
//        return "";
//    }

}

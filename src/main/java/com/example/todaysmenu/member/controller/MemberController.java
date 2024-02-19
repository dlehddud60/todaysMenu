package com.example.todaysmenu.member.controller;

import com.example.todaysmenu.board.common.modal.ComModal;
import com.example.todaysmenu.board.entity.Criteria;
import com.example.todaysmenu.board.entity.PageDTO;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.member.service.MemberService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


import jakarta.servlet.http.HttpServletRequest;
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

import static com.example.todaysmenu.board.common.modal.ComModal.DANGER;
import static com.example.todaysmenu.board.common.modal.ComModal.SUCCESS;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@Log4j2
public class MemberController {



    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;



    @RequestMapping("/join.do")
    public String join(HttpServletRequest request,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        log.info("============memberSession================{}",memberSession);
        log.info("회원가입 접속");
        if(memberSession != null) {
            return redirect("",rttr,"실패 메세지","로그인 유저는 진입하실 수 없습니다.",DANGER);
        }else{
            return "member/join";
        }
    }
    @RequestMapping("/updateLogin.do")
    public String updateLogin(HttpServletRequest request,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        log.info("============memberSession================{}",memberSession);
        if(memberSession == null) {
            return redirect("",rttr,"실패 메세지","비로그인 유저는 진입하실 수 없습니다.",DANGER);
        }else{
            return "member/join";
        }
    }
    @RequestMapping("/registerCheck.do")
    public @ResponseBody int registerCheck(@RequestParam("tmt_login_id") String tmt_login_id) {
        log.info("-------registerCheckMemberController-------");
        return memberService.registerCheck(tmt_login_id);
    }
    @RequestMapping("/register.do")
    public String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2,
                           RedirectAttributes rttr, HttpSession session, @RequestParam(value = "tmt_seq",required = false)String tmt_seq) {
        log.info("==========tmt_seq123========{}",tmt_seq);
        String register = memberService.register(memberDTO,tmt_pass_word1,tmt_pass_word2,rttr,session,tmt_seq);

      return register;
    }

    @RequestMapping("/logout.do")
    public String logout(RedirectAttributes rttr,HttpSession session) {

        session.invalidate();
        rttr.addFlashAttribute("msgType","로그아웃 메시지");
        rttr.addFlashAttribute("msg","로그아웃을 하였습니다.");
        rttr.addFlashAttribute("result",DANGER);
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


    @RequestMapping("/memberList.do")
    public String memberList(Criteria cri, Model model) {
        int total = memberService.count(cri);
        model.addAttribute("memberList", memberService.memberList(cri));
        model.addAttribute("pageMaker",new PageDTO(total,cri));

        return "member/memberList";
    }


}

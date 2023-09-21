package com.example.todaysmenu.member.controller;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class MemberController {

    String danger = "btn btn-danger";
    String success = "btn btn-success";

    @Autowired
    MemberRepository memberRepository;



    @RequestMapping("/join.do")
    public String join() {
        log.info("회원가입 접속");
        return "member/join";
    }
    @RequestMapping("/registerCheck.do")
    public @ResponseBody int registerCheck(@RequestParam("tmt_login_id") String tmt_login_id) {
        MemberDTO memberDTO = memberRepository.registerCheck(tmt_login_id);
        log.info("중복체크");
        if(memberDTO != null || tmt_login_id.equals("")) {
            return 0;
        }
        return 1;
    }
    @RequestMapping("/register.do")
    public String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2,
                           RedirectAttributes rttr, HttpSession session) {

        log.info("memberDTO:{}",memberDTO);
        if(memberDTO.getTmt_login_id() == null || memberDTO.getTmt_login_id().equals("")||
                tmt_pass_word1 == null || tmt_pass_word1.equals("")||
                tmt_pass_word2 == null || tmt_pass_word2.equals("")||
                memberDTO.getTmt_memb_name() == null || memberDTO.getTmt_memb_name().equals("")||
                memberDTO.getTmt_memb_age() == 0 ||
                memberDTO.getTmt_memb_gender() == null || memberDTO.getTmt_memb_gender().equals("")||
                memberDTO.getTmt_memb_email() == null || memberDTO.getTmt_memb_email().equals("")) {
            //누락메시지를 가지고 가기? => 객체 바인딩은 jsp로 갈 때 가능하다.

            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","모든 내용을 입력하세요 ");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }

        if(!tmt_pass_word1.equals(tmt_pass_word2)) {
            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","비밀번호가 서로 다릅니다.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }

        memberDTO.setTmt_memb_file("");//사진 이미지는 없다는 의미로 ""
        //회원을 테이블에 저장하기
        int result = memberRepository.register(memberDTO);
        if (result == 1) {//회원가입 성공 메시지
            log.info("===========회원가입 성공 메시지============");
            rttr.addFlashAttribute("msgType","회원가입 성공메시지");
            rttr.addFlashAttribute("msg","회원가입에 성공했습니다.");
            rttr.addFlashAttribute("result",success);
            //회원가입이 성공하면-> 로그인처리하기
            session.setAttribute("memberDTO",memberDTO); //${empty m}
            log.info("session{}",session);
            return "redirect:/";
        } else {
            rttr.addFlashAttribute("msgType","실패 메시지");
            rttr.addFlashAttribute("msg","이미 존재하는 회원입니다.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }
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

        if (memberDTO.getTmt_login_id() == null || memberDTO.getTmt_login_id().equals("")||
                memberDTO.getTmt_pass_word() == null || memberDTO.getTmt_pass_word().equals("")) {

            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","모든 내용을 입력해주세요");
            rttr.addFlashAttribute("result",danger);

            return "redirect:/loginForm.do";
        }
        if(memberRepository.login(memberDTO) != null) { //로그인 성공
            rttr.addFlashAttribute("msgType","성공 메세지");
            rttr.addFlashAttribute("msg","로그인에 성공했습니다.");
            rttr.addFlashAttribute("result",success);

            session.setAttribute("memberDTO",memberDTO); // ${empty mvo} 헤더에서 체크하고 있음
            return "redirect:/";
        }else{ //로그인 실패
            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","로그인에 실패했습니다.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/loginForm.do";

        }
    }

}

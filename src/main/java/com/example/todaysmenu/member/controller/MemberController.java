package com.example.todaysmenu.member.controller;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.memFile.DTO.MemFileDTO;
import com.example.todaysmenu.member.model.FindRequestLoginModel;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import com.example.todaysmenu.member.model.FindResponseMemberListModel;
import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.pagination.DTO.PageDTO;
import com.example.todaysmenu.member.DTO.MemberDTO;
import com.example.todaysmenu.member.service.MemberService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.DANGER;
import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.SUCCESS;
import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.redirect;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

   private final MemberService memberService;
    @RequestMapping("/join.do")
    public String join(HttpServletRequest request,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        if(memberSession != null) {
            return redirect("",rttr,"실패 메세지","로그인 유저는 진입하실 수 없습니다.",DANGER);
        }else{
            return "member/join";
        }
    }
    @RequestMapping("/updateLogin.do")
    public String updateLogin(HttpServletRequest request,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        if(memberSession == null) {
            return redirect("",rttr,"실패 메세지","비로그인 유저는 진입하실 수 없습니다.",DANGER);
        }else{
            return "member/join";
        }
    }
    @RequestMapping("/registerCheck.do")
    public @ResponseBody int registerCheck(@RequestParam("tmt_login_id") String tmt_login_id) {
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

        return redirect("",rttr,"로그아웃 메세지","로그아웃을 하였습니다.",DANGER);
    }
    @RequestMapping("loginForm.do")
    public String loginForm() {
        log.info("로그인");
        return "member/login";
    }
    @RequestMapping("login.do")
    public String login(FindRequestLoginModel findRequestLoginModel, RedirectAttributes rttr, HttpSession session) {

        String login = memberService.login(findRequestLoginModel,rttr,session);
        return login;
    }
    @RequestMapping("/imageForm.do")
    public String imageForm(HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        if(memberSession == null) {
            return redirect("",rttr,"실패 메시지","로그인을 해주시길 바랍니다.", DANGER);
        }
        return "member/imageForm";
    }


    @RequestMapping("/memberList.do")
    public String memberList(HttpServletRequest request,Criteria cri, Model model,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        String userType = "";
        try {
            userType = memberSession.tmt_user_type();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }finally {
            if(!userType.equals("master") || userType.equals("user") || userType == null){
                return redirect("",rttr,"실패 메세지","관리자 등급만 진입하실 수 있습니다.",DANGER);
            }
        }
        int total = memberService.count(cri);
        model.addAttribute("memberList", memberService.memberList(cri));
        model.addAttribute("pageMaker",new PageDTO(total,cri));

        return "member/memberList";
    }

    @RequestMapping("/userTypeUpdate.do")
    public String userTypeUpdate(MemberDTO memberDTO,RedirectAttributes rttr,HttpServletRequest request) {
        memberService.userTypeUpdate(memberDTO,request);
        return redirect("memberList.do",rttr,"성공 메세지","등급 변경에 성공 하셨습니다.",SUCCESS);
    }

    @RequestMapping("/imageUpdate.do")
    public String memImageUpdate(@ModelAttribute MemFileDTO memFileDTO,HttpServletRequest request, RedirectAttributes rttr) {

        log.info("==============imageUpdate============");


        try {
            memberService.memImageUpdate(memFileDTO,request);
        } catch (FileExtensionExaption | FileSizeExaption e) {
            return redirect("",rttr,"실패",e.getMessage(),DANGER);

        }


        return redirect("",rttr,"성공 메세지","파일첨부를 완료했습니다.",SUCCESS);


    }

}

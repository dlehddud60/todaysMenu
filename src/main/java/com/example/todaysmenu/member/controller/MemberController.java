package com.example.todaysmenu.member.controller;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.pagination.entity.PageDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

import static com.example.todaysmenu.board.common.modal.ComModal.DANGER;
import static com.example.todaysmenu.board.common.modal.ComModal.SUCCESS;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;

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
    public String login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session) {

        String login = memberService.login(memberDTO,rttr,session);
        return login;
    }
    @RequestMapping("/imageForm.do")
    public String imageForm() {
        return "member/imageForm";
    }


    @RequestMapping("/memberList.do")
    public String memberList(HttpServletRequest request,Criteria cri, Model model,RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        String userType = "";
        try {
            userType = memberSession.getTmt_user_type();
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

//    @RequestMapping("/imageUpdate.do")
//    public String memImageUpdate(HttpServletRequest request, javax.servlet.http.HttpSession session, RedirectAttributes rttr) {
//        //파일 업로드 API(cos.jar,3가지가 있다.)
//        MultipartRequest multi = null;
//
//        int fileMAxSize = 10*1024*1024; //10mb
//        String savePath = request.getRealPath("static/upload");
//
//        try {                                                                               //1-1.png같이 리네임함
////            이미지 업로드
//            multi = new MultipartRequest(request,savePath,fileMAxSize,"UTF-8",new DefaultFileRenamePolicy());
//        } catch (Exception e) {
//            e.printStackTrace();//오류 메세지 띄우기
//            return redirect("imageForm.do",rttr,"실패 메세지","파일의 크기는 10MB를 넘을 수 없습니다.",DANGER);
//        }
//        //데이터베이스 테이블에 회원이미지를 업데이트 한다.
//        String memID = multi.getParameter("memID");
//        String newProfile = "";
//        File file = multi.getFile("memProfile");
//        if (file != null) {//업로드가 된 상태(png. jpg, gif)
//            //이미지 파일 여부를 체크 -> 만약 이미지 파일이 아니면 삭제
//            String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
//            ext = ext.toUpperCase();
//            if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")) {
//                //새로 업로드된 이미지(new 1.png), 현재 DB에 있는 이미지(old 4.png)
//                String oldProfile = memberMapper.getMember(memID).getMemProfile();
//                File oldFile = new File(savePath + "/" + oldProfile);
//                if (oldFile.exists()) {
//                    oldFile.delete();
//                }
//                newProfile = file.getName();
//
//
//            }else { //이미지 파일이 아니면 이미지를 삭제
//                if (file.exists()) {
//                    file.delete();//삭제
//                }
//                rttr.addFlashAttribute("msgType","실패 메시지");
//                rttr.addFlashAttribute("msg","이미지 파일만 업로드 가능합니다.");
//                return "redirect:/memImageForm.do";
//            }
//        }
//        //새로운 이미지를 테이블에 업데이트
//
//        Member mvo = new Member();
//        mvo.setMemID(memID);
//        mvo.setMemProfile(newProfile);
//        memberMapper.memProfileUpdate(mvo); //이미지 업데이트 성공
//        Member m = memberMapper.getMember(memID);
//        //세션을 새롭게 생성한다.
//        session.setAttribute("mvo",m);
//
//        rttr.addFlashAttribute("msgType","성공 메시지");
//        rttr.addFlashAttribute("msg","이미지 변경에 성공했습니다.");
//        return "redirect:/";
//         return redirect("",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);

//    }

}

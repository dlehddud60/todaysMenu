package com.example.todaysmenu.member.service.impl;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.regex.*;

import java.sql.SQLException;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {

    String danger = "btn btn-danger";
    String success = "btn btn-success";


    @Autowired
    MemberRepository memberRepository;

    @Override
    public int registerCheck(String tmt_login_id) {
        MemberDTO memberDTO = memberRepository.registerCheck(tmt_login_id);
        log.info("중복체크");
        if(memberDTO != null || tmt_login_id.equals("")) {
            return 0;
        }
        return 1;
    }

    @Override
    public String  register(MemberDTO memberDTO
            , String tmt_pass_word1
            , String tmt_pass_word2
            , RedirectAttributes rttr
            , HttpSession session
            , String tmt_seq
            ) {
        String loginId = memberDTO.getTmt_login_id();
        Pattern idChk = Pattern.compile("^[a-z0-9]+$");
        Matcher idMatcher = idChk.matcher(loginId);

        String pass = memberDTO.getTmt_pass_word();
        Pattern passChk = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher passMatcher = passChk.matcher(pass);

        String email = memberDTO.getTmt_memb_email();
        Pattern emailChk = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        Matcher emailMatcher = emailChk.matcher(email);


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
        if(passMatcher.matches() || pass.length() < 8){
            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","비밀번호는 영어 소문자,특수문자,숫자로 구성된 8글자 이상으로 조합하시오.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }else{
            if(!tmt_pass_word1.equals(tmt_pass_word2)) {
                rttr.addFlashAttribute("msgType","실패 메세지");
                rttr.addFlashAttribute("msg","비밀번호가 서로 다릅니다.");
                rttr.addFlashAttribute("result",danger);
                return "redirect:/join.do";
            }
        }


        if(!idMatcher.matches() || loginId.length() < 6) {
            rttr.addFlashAttribute("msgType","아이디 유효성 검사");
            rttr.addFlashAttribute("msg","아이디는 영소문자,숫자로 구성된 6글자 이상으로 조합하시오.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }

        if(!emailMatcher.matches()) {
            rttr.addFlashAttribute("msgType","이메일 유효성 검사");
            rttr.addFlashAttribute("msg","이메일 형식에 맞게 입력해주세요.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/join.do";
        }

        memberDTO.setTmt_memb_file("");//사진 이미지는 없다는 의미로 ""
        //회원을 테이블에 저장하기
        int result = 0;
        try {
            String msg = "";

            if(tmt_seq != null && !tmt_seq.equals("")){
                result = memberRepository.memberUpdate(memberDTO);
                msg = "회원정보 수정";
            }else{
                result = memberRepository.register(memberDTO);
                msg = "회원가입";
            }
            // 성공적으로 등록된 경우 처리할 로직
            if (result == 1) {
                rttr.addFlashAttribute("msgType", msg + " 성공메시지");
                rttr.addFlashAttribute("msg", msg+ "에 성공했습니다.");
                rttr.addFlashAttribute("result", success);
                // 회원가입이 성공하면-> 로그인처리하기
                session.setAttribute("memberDTO", memberDTO); // ${empty m}
                log.info("session{}", session);
                return "redirect:/";
            }
        } catch (Exception e) {
            log.info("result{}",result);
            if(result == 0) {
                rttr.addFlashAttribute("msgType", "실패 메시지");
                rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
                rttr.addFlashAttribute("result", danger);
                return "redirect:/join.do";
            }
            // 무결성 제약 조건 위배 예외 처리
            String errorMessage = e.getMessage(); // 예외 메시지를 가져올 수 있습니다.
        }
        return "redirect:/";
    }


    @Override
    public String  login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session) {
        if (memberDTO.getTmt_login_id() == null || memberDTO.getTmt_login_id().equals("")||
                memberDTO.getTmt_pass_word() == null || memberDTO.getTmt_pass_word().equals("")) {

            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","모든 내용을 입력해주세요");
            rttr.addFlashAttribute("result",danger);

            return "redirect:/loginForm.do";
        }
        MemberDTO mvo = memberRepository.login(memberDTO);
        if(mvo != null) { //로그인 성공
            rttr.addFlashAttribute("msgType","성공 메세지");
            rttr.addFlashAttribute("msg","로그인에 성공했습니다.");
            rttr.addFlashAttribute("result",success);

            session.setAttribute("memberDTO",mvo); // ${empty mvo} 헤더에서 체크하고 있음
            return "redirect:/";
        }else{ //로그인 실패
            rttr.addFlashAttribute("msgType","실패 메세지");
            rttr.addFlashAttribute("msg","로그인에 실패했습니다.");
            rttr.addFlashAttribute("result",danger);
            return "redirect:/loginForm.do";

        }
    }
}

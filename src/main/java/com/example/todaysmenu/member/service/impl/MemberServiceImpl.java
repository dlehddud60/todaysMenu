package com.example.todaysmenu.member.service.impl;

import com.example.todaysmenu.board.common.modal.ComModal;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.member.service.MemberService;
import com.oreilly.servlet.MultipartRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.regex.*;

import java.sql.SQLException;

import static com.example.todaysmenu.board.common.modal.ComModal.danger;
import static com.example.todaysmenu.board.common.modal.ComModal.success;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {




    @Autowired
    MemberRepository memberRepository;
    private boolean duplChek;

    @Override
    public int registerCheck(String tmt_login_id) {
        MemberDTO memberDTO = memberRepository.registerCheck(tmt_login_id);
        log.info("중복체크");
        if(memberDTO != null || tmt_login_id.equals("")) {
            try{
                duplChkMethod();
            } catch (Exception e) {
                duplChek = true;
            }
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
        if(duplChek) {
            redirect("join.do",rttr,"아이디 중복체크","중복된 아이디 입니다. 다른 아이디를 입력해주시길 바랍니다.",danger);
            return "redirect:/join.do";

        }
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
            ComModal.redirect("join.do",rttr,"실패 메세지","모든 내용을 입력하세요 ", danger);
        }
        if(passMatcher.matches() || pass.length() < 8){
            ComModal.redirect("join.do",rttr,"실패 메세지","비밀번호는 영어 소문자,특수문자,숫자로 구성된 8글자 이상으로 조합하시오.",danger);
        }else{
            if(!tmt_pass_word1.equals(tmt_pass_word2)) {
                ComModal.redirect("join.do",rttr,"실패 메세지","비밀번호가 서로 다릅니다.",danger);
            }
        }



        if(!idMatcher.matches() || loginId.length() < 6) {
            redirect("join.do",rttr,"아이디 유효성 검사","아이디는 영소문자,숫자로 구성된 6글자 이상으로 조합하시오.",danger);
        }

        if(!emailMatcher.matches()) {
            redirect("join.do",rttr,"이메일 유효성 검사","이메일 형식에 맞게 입력해주세요.",danger);
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
                // 회원가입이 성공하면-> 로그인처리하기
                session.setAttribute("memberDTO", memberDTO); // ${empty m}
                log.info("session{}", session);
                redirect("join.do",rttr,msg + " 성공메시지",msg+ "에 성공했습니다.",success);
            }
        } catch (Exception e) {
            log.info("result{}",result);
            if(result == 0) {
                redirect("join.do",rttr,"실패 메시지","이미 존재하는 회원입니다.",danger);
            }
            // 무결성 제약 조건 위배 예외 처리
            String errorMessage = e.getMessage(); // 예외 메시지를 가져올 수 있습니다.
        }
        return "redirect:/";
    }


    @Override
    public String login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session) {
        if (memberDTO.getTmt_login_id() == null || memberDTO.getTmt_login_id().equals("")||
                memberDTO.getTmt_pass_word() == null || memberDTO.getTmt_pass_word().equals("")) {
            redirect("loginForm.do",rttr,"실패 메시지","모든 내용을 입력해주세요",danger);
        }
        MemberDTO mvo = null;
        String duplLoginErrorMsg = "로그인에 실패했습니다.";
        try {
            mvo = memberRepository.login(memberDTO);

        }catch (Exception e) {
            duplLoginErrorMsg = "중복 로그인이 발생하였습니다.";
        }
        if(mvo != null) { //로그인 성공
            session.setAttribute("memberDTO",mvo); // ${empty mvo} 헤더에서 체크하고 있음
            redirect("",rttr,"성공 메세지","로그인에 성공했습니다.",success);
        }else{ //로그인 실패
            redirect("loginForm.do",rttr,"실패 메시지",duplLoginErrorMsg,danger);
        }
        return "redirect:/";
    }

    public static void duplChkMethod() throws Exception {
        throw new Exception("아이디 중복이 발생하였습니다.");
    }
}


package com.example.todaysmenu.member.service.impl;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.member.entity.file.MemFileDTO;
import com.example.todaysmenu.member.repository.file.MemFileRepository;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import static com.example.todaysmenu.board.common.modal.ComModal.DANGER;
import static com.example.todaysmenu.board.common.modal.ComModal.SUCCESS;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;

@Service
@Log4j2
public class MemberServiceImpl implements MemberService {




    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemFileRepository memFileRepository;

    private boolean duplChek;



    @Override
    public int registerCheck(String tmt_login_id) {
        MemberDTO memberDTO = memberRepository.registerCheck(tmt_login_id);
        log.info("중복체크");
        if(memberDTO != null || tmt_login_id.equals("")) {
                duplChek = true;
            return 0;
        }else{
            duplChek = false;
            return 1;
        }
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
        if(loginId == null || loginId.equals("")) {
            MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
            memberDTO.setTmt_login_id(memberSession.getTmt_login_id());
            loginId = memberSession.getTmt_login_id();

        }
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
            return redirect("join.do",rttr,"실패 메세지","모든 내용을 입력하세요 ", DANGER);
        }
        if(passMatcher.matches() || pass.length() < 8){

            return redirect("join.do",rttr,"실패 메세지","비밀번호는 영어 소문자,특수문자,숫자로 구성된 8글자 이상으로 조합하시오.", DANGER);
        }
        if(!tmt_pass_word1.equals(tmt_pass_word2)) {
            return redirect("join.do",rttr,"실패 메세지","비밀번호가 서로 다릅니다.", DANGER);
        }
        if(!idMatcher.matches() || loginId.length() < 6) {
            return redirect("join.do",rttr,"아이디 유효성 검사","아이디는 영소문자,숫자로 구성된 6글자 이상으로 조합하시오.", DANGER);
        }
        if(!emailMatcher.matches()) {
            return redirect("join.do",rttr,"이메일 유효성 검사","이메일 형식에 맞게 입력해주세요.", DANGER);
        }
        if(duplChek) {
            return redirect("join.do",rttr,"아이디 중복체크","중복된 아이디 입니다. 다른 아이디를 입력해주시길 바랍니다.", DANGER);
        }


        //회원을 테이블에 저장하기
        int result = 0;
        try {
            String msg = "";
            String encodePassWord = passwordEncoder.encode(memberDTO.getTmt_pass_word());
            memberDTO.setTmt_pass_word(encodePassWord);
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
                return redirect("",rttr,msg + " 성공메시지",msg+ "에 성공했습니다.", SUCCESS);
            }
        } catch (Exception e) {
            log.info("result{}",result);
            if(result == 0) {
                return redirect("join.do",rttr,"실패 메시지","이미 존재하는 회원입니다.", DANGER);
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
            return redirect("loginForm.do",rttr,"실패 메시지","모든 내용을 입력해주세요", DANGER);
        }
        MemberDTO mvo = null;
        String duplLoginErrorMsg = "로그인에 실패했습니다.";
        try {
            mvo = memberRepository.login(memberDTO);

        }catch (Exception e) {
            duplLoginErrorMsg = "중복 로그인이 발생하였습니다.";
        }
        //비밀번호 일치 여부 체크
        if(mvo != null && passwordEncoder.matches(memberDTO.getTmt_pass_word(), mvo.getTmt_pass_word())) { //로그인 성공
            session.setAttribute("memberDTO",mvo); // ${empty mvo} 헤더에서 체크하고 있음
            return redirect("",rttr,"성공 메세지","로그인에 성공했습니다.", SUCCESS);
        }else{ //로그인 실패
            return redirect("loginForm.do",rttr,"실패 메시지",duplLoginErrorMsg, DANGER);
        }
    }

    @Override
    public List<MemberDTO> memberList(Criteria cri) {
        return memberRepository.memberList(cri);
    }

    @Override
    public int count(Criteria cri) {
        return memberRepository.count(cri);
    }

    @Override
    public void userTypeUpdate(MemberDTO memberDTO, HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String[] tmtSeqArr = paramMap.get("tmt_seq");
        for (int i = 0; i < tmtSeqArr.length; i++) {
            memberDTO.setTmt_seq(tmtSeqArr[i]);
            memberRepository.userTypeUpdate(memberDTO);
            }
        }

    @Override
    public void memImageUpdate(MemFileDTO memFileDTO,HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");

        memFileDTO.setTmft_input_ty(memberSession.getTmt_user_type());
        memFileDTO.setTmft_input_nm(memberSession.getTmt_memb_name());
        memFileDTO.setTmft_input_id(memberSession.getTmt_login_id());
        memFileDTO.setTmft_input_ip(request.getRemoteAddr());


        // 파일만 따로 가져오기
        MultipartFile boardFile = memFileDTO.getTmt_multi_file();
        // 파일 이름 가져오기
        String originalFilename = boardFile.getOriginalFilename();
        log.info("=================originalFilename=============={} ",originalFilename);
        // 저장용 이름 만들기
        System.out.println(System.currentTimeMillis());
        String storedFileName = System.currentTimeMillis() + "-" + originalFilename;
        log.info("=================storedFileName=============={} ",storedFileName);

        // BoardFileDTO 세팅
        memFileDTO.setTmft_origin_file_name(originalFilename);
        memFileDTO.setTmft_change_fine_name(storedFileName);
        memFileDTO.setTmft_parent_seq(Integer.parseInt( memberSession.getTmt_seq()));
        log.info("===============memFileDTO==============={}",memFileDTO);
        // 파일 저장용 폴더에 파일 저장 처리
        String savePath = "/Users/leedongyoung/app/2023/workspace/todaysMenu/src/main/resources/static/upload/" + storedFileName; // mac
        try {
            boardFile.transferTo(new File(savePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    // board_file_table 저장 처리
        memFileRepository.insert(memFileDTO);
    }
}


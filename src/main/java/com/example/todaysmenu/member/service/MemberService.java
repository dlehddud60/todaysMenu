package com.example.todaysmenu.member.service;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.member.entity.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface MemberService {
    public int registerCheck(String tmt_login_id);
    public String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2, RedirectAttributes rttr, HttpSession session, String tmt_seq);
    public String login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session);
    public List<MemberDTO> memberList(Criteria cri);
    public int count(Criteria cri);
    public void userTypeUpdate(MemberDTO memberDTO, HttpServletRequest request);
}

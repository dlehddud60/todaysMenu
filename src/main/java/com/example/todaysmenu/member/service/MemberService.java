package com.example.todaysmenu.member.service;

import com.example.todaysmenu.member.entity.MemberDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface MemberService {
    public int registerCheck(String tmt_login_id);
    public String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2, RedirectAttributes rttr, HttpSession session);
    public String login(MemberDTO memberDTO, RedirectAttributes rttr, HttpSession session);
}

package com.example.todaysmenu.member.service;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.memFile.DTO.MemFileDTO;
import com.example.todaysmenu.member.model.FindRequestLoginModel;
import com.example.todaysmenu.member.model.FindResponseMemberListModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.member.DTO.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface MemberService {
    int registerCheck(String tmt_login_id);
    String register(MemberDTO memberDTO, String tmt_pass_word1, String tmt_pass_word2, RedirectAttributes rttr, HttpSession session, String tmt_seq);
    String login(FindRequestLoginModel findRequestLoginModel, RedirectAttributes rttr, HttpSession session);
    List<FindResponseMemberListModel> memberList(Criteria cri);
    int count(Criteria cri);
    void userTypeUpdate(MemberDTO memberDTO, HttpServletRequest request);
    void memImageUpdate(MemFileDTO memFileDTO,HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;
}

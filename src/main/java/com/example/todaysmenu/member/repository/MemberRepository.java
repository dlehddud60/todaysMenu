package com.example.todaysmenu.member.repository;

import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.member.DTO.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    MemberDTO registerCheck(String tmt_login_id);
    int register(MemberDTO memberDTO);
    MemberDTO login(MemberDTO memberDTO);
    String memberInfo(String tmt_seq);
    int memberUpdate(MemberDTO memberDTO);
    List<MemberDTO> memberList(Criteria cri);
    int count(Criteria cri);
    void userTypeUpdate(MemberDTO memberDTO);

}

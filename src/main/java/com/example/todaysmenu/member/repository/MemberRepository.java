package com.example.todaysmenu.member.repository;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.member.entity.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    public MemberDTO registerCheck(String tmt_login_id);
    public int register(MemberDTO memberDTO);
    public MemberDTO login(MemberDTO memberDTO);
    public String memberInfo(String tmt_seq);
    public int memberUpdate(MemberDTO memberDTO);
    public List<MemberDTO> memberList(Criteria cri);
    public int count(Criteria cri);
    public void userTypeUpdate(MemberDTO memberDTO);

}

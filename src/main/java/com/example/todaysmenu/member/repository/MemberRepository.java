package com.example.todaysmenu.member.repository;

import com.example.todaysmenu.member.model.FindRequestLoginModel;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import com.example.todaysmenu.member.model.FindResponseMemberListModel;
import com.example.todaysmenu.member.model.FindResponseRegisterCheckModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.member.DTO.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {
    FindResponseRegisterCheckModel registerCheck(String tmt_login_id);
    int register(MemberDTO memberDTO);
    FindResponseLoginModel login(FindRequestLoginModel findRequestLoginModel);
    int memberUpdate(MemberDTO memberDTO);
    List<FindResponseMemberListModel> memberList(Criteria cri);
    int count(Criteria cri);
    void userTypeUpdate(MemberDTO memberDTO);

}

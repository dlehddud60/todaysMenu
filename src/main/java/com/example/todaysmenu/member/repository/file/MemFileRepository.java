package com.example.todaysmenu.member.repository.file;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.entity.file.MemFileDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemFileRepository {
    int count(Criteria cri);
    MemFileDTO list(MemFileDTO memFileDTO);
    MemFileDTO list2(int tmt_seq);

    int insert(MemFileDTO memFileDTO);
    int update(MemFileDTO memFileDTO);

}

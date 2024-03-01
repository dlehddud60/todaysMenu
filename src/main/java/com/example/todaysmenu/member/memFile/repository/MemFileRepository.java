package com.example.todaysmenu.member.memFile.repository;

import com.example.todaysmenu.member.memFile.entity.MemFileDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemFileRepository {
    int count(Criteria cri);
    MemFileDTO list(MemFileDTO memFileDTO);
    MemFileDTO list2(int tmt_seq);

    int insert(MemFileDTO memFileDTO);
    int update(MemFileDTO memFileDTO);

}

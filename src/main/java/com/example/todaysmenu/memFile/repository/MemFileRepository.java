package com.example.todaysmenu.memFile.repository;

import com.example.todaysmenu.memFile.entity.MemFileDTO;
import com.example.todaysmenu.pagination.DTO.Criteria;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemFileRepository {
    int count(Criteria cri);
    MemFileDTO list(MemFileDTO memFileDTO);
    MemFileDTO list2(int tmt_seq);

    int insert(MemFileDTO memFileDTO);
    int update(MemFileDTO memFileDTO);

}

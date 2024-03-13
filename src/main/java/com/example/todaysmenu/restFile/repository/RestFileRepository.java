package com.example.todaysmenu.restFile.repository;

import com.example.todaysmenu.restFile.DTO.RestFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestFileRepository {
    List<RestFileDTO> list(RestFileDTO restFileDTO);
    int insert(RestFileDTO restFileDTO);
    int update(RestFileDTO restFileDTO);
    int delete(int trft_parent_seq);

}

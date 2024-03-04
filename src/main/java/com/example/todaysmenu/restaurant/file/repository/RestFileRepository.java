package com.example.todaysmenu.restaurant.file.repository;

import com.example.todaysmenu.restaurant.file.entity.RestFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestFileRepository {
    List<RestFileDTO> list(RestFileDTO restFileDTO);
    int insert(RestFileDTO restFileDTO);
    int update(RestFileDTO restFileDTO);
    int delete(int trft_parent_seq);

}

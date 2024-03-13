package com.example.todaysmenu.star.repository;

import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.star.DTO.RestStarDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestStarRepository {
    int count(Criteria cri);
    List<RestStarDTO> list(int trst_seq);
    List<RestStarDTO> listPaging(Criteria cri);
    RestStarDTO info(int trst_seq);
    int insert(RestStarDTO restaurantDTO);
    int update(RestStarDTO restaurantDTO);
    int delete(int trst_seq);
    int delete(RestStarDTO restStarDTO);
}

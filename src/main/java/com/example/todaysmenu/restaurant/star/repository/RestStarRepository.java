package com.example.todaysmenu.restaurant.star.repository;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.star.entity.RestStarDTO;
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
}

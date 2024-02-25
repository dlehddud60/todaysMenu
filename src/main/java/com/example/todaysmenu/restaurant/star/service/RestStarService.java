package com.example.todaysmenu.restaurant.star.service;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.star.entity.RestStarDTO;

import java.util.List;

public interface RestStarService {
    int count(Criteria cri);
    List<RestStarDTO> list(Criteria cri);
    RestStarDTO info(int trt_seq);
    int insert(RestStarDTO restStarDTO);
    int update(RestStarDTO restStarDTO);
    int delete(int trst_seq);

}

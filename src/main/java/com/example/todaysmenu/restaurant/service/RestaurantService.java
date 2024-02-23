package com.example.todaysmenu.restaurant.service;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;

import java.util.List;

public interface RestaurantService {
    int count(Criteria cri);
    List<RestaurantDTO> list(Criteria cri);
    RestaurantDTO info(int trt_seq);
    int insert(RestaurantDTO restaurantDTO);
    int update(RestaurantDTO restaurantDTO);
    int delete(int trt_seq);
    int updateCount(int trt_seq);

}

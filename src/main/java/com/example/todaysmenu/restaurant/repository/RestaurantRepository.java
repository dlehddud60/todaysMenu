package com.example.todaysmenu.restaurant.repository;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantRepository {
    int count(Criteria cri);
    List<RestaurantDTO> list();
    List<RestaurantDTO> listPaging(Criteria cri);
    RestaurantDTO info(int trt_seq);
    int insert(RestaurantDTO restaurantDTO);
    int update(RestaurantDTO restaurantDTO);
    int delete(int trt_seq);
    int updateCount(int trt_seq);
}

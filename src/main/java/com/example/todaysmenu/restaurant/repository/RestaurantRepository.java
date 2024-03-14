package com.example.todaysmenu.restaurant.repository;

import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.restaurant.DTO.RestaurantDTO;
import com.example.todaysmenu.restaurant.model.FindResponseRestaurantListModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantRepository {
    int count(Criteria cri);
    List<RestaurantDTO> list();
    List<FindResponseRestaurantListModel> listPaging(Criteria cri);
    RestaurantDTO info(RestaurantDTO restaurantDTO);
    RestaurantDTO info(int restaurantDTO);
    int insert(RestaurantDTO restaurantDTO);
    int update(RestaurantDTO restaurantDTO);
    int delete(int trt_seq);
    int updateCount(int trt_seq);
}

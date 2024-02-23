package com.example.todaysmenu.restaurant.menu.service;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;

import java.util.List;

public interface RestMenuService {
    int count(Criteria cri);
    List<RestMenuDTO> list();
    RestMenuDTO info(int trmt_seq);
    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trmt_seq);

}

package com.example.todaysmenu.restaurant.menu.service.impl;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import com.example.todaysmenu.restaurant.menu.repository.RestMenuRepository;
import com.example.todaysmenu.restaurant.menu.service.RestMenuService;
import com.example.todaysmenu.restaurant.repository.RestaurantRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RestMenuServiceImpl implements RestMenuService {

    @Autowired
    RestMenuRepository restMenuRepository;


    @Override
    public int count(Criteria cri) {
        return restMenuRepository.count(cri);
    }

    @Override
    public List<RestMenuDTO> list() {
        return restMenuRepository.list();
    }

    @Override
    public RestMenuDTO info(int trmt_seq) {
        return restMenuRepository.info(trmt_seq);
    }

    @Override
    public int insert(RestMenuDTO restaurantDTO) {
        return restMenuRepository.insert(restaurantDTO);
    }

    @Override
    public int update(RestMenuDTO restaurantDTO) {
        return restMenuRepository.update(restaurantDTO);
    }

    @Override
    public int delete(int trmt_seq) {
        return restMenuRepository.delete(trmt_seq);
    }
}
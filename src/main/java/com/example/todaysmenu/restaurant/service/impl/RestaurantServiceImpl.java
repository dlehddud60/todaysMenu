package com.example.todaysmenu.restaurant.service.impl;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.repository.RestaurantRepository;
import com.example.todaysmenu.restaurant.service.RestaurantService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public int count(Criteria cri) {
        return restaurantRepository.count(cri);
    }

    @Override
    public List<RestaurantDTO> list(Criteria cri) {
        return restaurantRepository.listPaging(cri);
    }

    @Override
    public RestaurantDTO info(int trt_seq) {
        return restaurantRepository.info(trt_seq);
    }

    @Override
    public int insert(RestaurantDTO restaurantDTO) {
       return restaurantRepository.insert(restaurantDTO);
    }

    @Override
    public int update(RestaurantDTO restaurantDTO) {
        return restaurantRepository.update(restaurantDTO);
    }

    @Override
    public int delete(int trt_seq) {
        return restaurantRepository.delete(trt_seq);
    }

    @Override
    public int updateCount(int trt_seq) {
        return restaurantRepository.updateCount(trt_seq);
    }
}

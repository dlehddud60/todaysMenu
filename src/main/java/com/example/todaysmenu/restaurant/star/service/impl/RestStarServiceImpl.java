package com.example.todaysmenu.restaurant.star.service.impl;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.repository.RestaurantRepository;
import com.example.todaysmenu.restaurant.star.entity.RestStarDTO;
import com.example.todaysmenu.restaurant.star.repository.RestStarRepository;
import com.example.todaysmenu.restaurant.star.service.RestStarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RestStarServiceImpl implements RestStarService {

    @Autowired
    RestStarRepository restStarRepository;

    @Override
    public int count(Criteria cri) {
        return restStarRepository.count(cri);
    }

    @Override
    public List<RestStarDTO> list(Criteria cri) {
        return restStarRepository.listPaging(cri);
    }

    @Override
    public RestStarDTO info(int trt_seq) {
        return restStarRepository.info(trt_seq);
    }

    @Override
    public int insert(RestStarDTO restStarDTO) {
       return restStarRepository.insert(restStarDTO);
    }

    @Override
    public int update(RestStarDTO restStarDTO) {
        return restStarRepository.update(restStarDTO);
    }

    @Override
    public int delete(int trst_seq) {
        return restStarRepository.delete(trst_seq);
    }

    @Override
    public int delete(RestStarDTO restStarDTO) {
        return restStarRepository.delete(restStarDTO);
    }
}

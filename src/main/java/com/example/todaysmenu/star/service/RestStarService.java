package com.example.todaysmenu.star.service;

import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.star.DTO.RestStarDTO;

import java.util.List;

public interface RestStarService {
    int count(Criteria cri);
    List<RestStarDTO> list(Criteria cri);
    RestStarDTO info(int trt_seq);
    int insert(RestStarDTO restStarDTO);
    int update(RestStarDTO restStarDTO);
    int delete(int trst_seq);
    int delete(RestStarDTO restStarDTO);


}

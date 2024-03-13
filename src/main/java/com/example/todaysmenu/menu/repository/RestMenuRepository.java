package com.example.todaysmenu.menu.repository;

import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.menu.DTO.RestMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestMenuRepository {
    int count(Criteria cri);
    List<RestMenuDTO> list(RestMenuDTO restMenuDTO);
    List<RestMenuDTO> listPaging(Criteria cri);
    List<RestMenuDTO> rentMenuList(int trt_seq);
    List<RestMenuDTO> rentMenuList(RestMenuDTO restMenuDTO);

    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trt_seq);
    int parentDel(int trt_seq);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO);
}

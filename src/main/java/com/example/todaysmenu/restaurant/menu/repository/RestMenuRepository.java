package com.example.todaysmenu.restaurant.menu.repository;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestMenuRepository {
    int count(Criteria cri);
    List<RestMenuDTO> list();
    List<RestMenuDTO> listPaging(Criteria cri);
    List<RestMenuDTO> rentMenuList(int trt_seq);
    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trt_seq);
    int parentDel(int trt_seq);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO);
}

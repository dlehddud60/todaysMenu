package com.example.todaysmenu.restaurant.menu.service;

import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface RestMenuService {
    int count(Criteria cri);
    List<RestMenuDTO> list();
    List<RestMenuDTO> listPaging(Criteria cri);
    List<RestMenuDTO> rentMenuList(int trt_seq);
    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trmt_seq);
    int parentDel(int trt_seq);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO, HttpServletRequest request, RedirectAttributes rttr);



}

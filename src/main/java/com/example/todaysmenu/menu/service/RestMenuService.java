package com.example.todaysmenu.menu.service;

import com.example.todaysmenu.menu.model.FindResponseMenuListModel;
import com.example.todaysmenu.menu.model.FindResponseSubMenuListModel;
import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.menu.DTO.RestMenuDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface RestMenuService {
    int count(Criteria cri);
    List<FindResponseSubMenuListModel> list(RestMenuDTO restMenuDTO);
    List<FindResponseMenuListModel> listPaging(Criteria cri);
    List<RestMenuDTO> rentMenuList(int trt_seq);
    List<RestMenuDTO> rentMenuList(RestMenuDTO restMenuDTO);
    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trmt_seq);
    int parentDel(int trt_seq);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO, HttpServletRequest request, RedirectAttributes rttr);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO, HttpServletRequest request, RedirectAttributes rttr, Model model);



}

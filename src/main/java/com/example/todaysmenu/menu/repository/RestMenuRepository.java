package com.example.todaysmenu.menu.repository;

import com.example.todaysmenu.menu.model.FindResponseMenuListModel;
import com.example.todaysmenu.menu.model.FindResponseSubMenuListModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.menu.DTO.RestMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestMenuRepository {
    int count(Criteria cri);
    List<FindResponseSubMenuListModel> list(RestMenuDTO restMenuDTO); // 추천페이지 서브메뉴 리스트에서 사용
    List<FindResponseMenuListModel> listPaging(Criteria cri); // 메뉴 리스트에서 사용
    List<RestMenuDTO> rentMenuList(int trt_seq);
    List<RestMenuDTO> rentMenuList(RestMenuDTO restMenuDTO); // 식당 상세보기의 메뉴 리스트에서 사용

    int insert(RestMenuDTO restaurantDTO);
    int update(RestMenuDTO restaurantDTO);
    int delete(int trt_seq);
    int parentDel(int trt_seq);
    RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO);
}

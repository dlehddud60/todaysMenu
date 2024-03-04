package com.example.todaysmenu.restaurant.service;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.file.entity.RestFileDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RestaurantService {
    int count(Criteria cri);
    List<RestaurantDTO> list(Criteria cri);
    RestaurantDTO info(RestaurantDTO restaurantDTO);
    RestaurantDTO info(int trt_Seq);
    int insert(RestaurantDTO restaurantDTO);
    int insert(RestaurantDTO restaurantDTO, RestFileDTO restFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;

    int update(RestaurantDTO restaurantDTO);
    int update(RestaurantDTO restaurantDTO, RestFileDTO restFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption;

    int delete(int trt_seq);
    int delete(int trt_seq,RestFileDTO restFileDTO);
    int updateCount(int trt_seq);

}

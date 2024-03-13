package com.example.todaysmenu.restaurant.service;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.restaurant.DTO.RestaurantDTO;
import com.example.todaysmenu.restFile.DTO.RestFileDTO;
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

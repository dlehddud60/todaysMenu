package com.example.todaysmenu.restaurant.file.service;

import com.example.todaysmenu.restaurant.file.entity.RestFileDTO;

import java.util.List;

public interface RestFileService {
    List<RestFileDTO> list(RestFileDTO restFileDTO);
    int insert(RestFileDTO restFileDTO);
}

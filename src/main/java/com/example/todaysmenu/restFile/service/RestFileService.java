package com.example.todaysmenu.restFile.service;

import com.example.todaysmenu.restFile.DTO.RestFileDTO;

import java.util.List;

public interface RestFileService {
    List<RestFileDTO> list(RestFileDTO restFileDTO);
    int insert(RestFileDTO restFileDTO);
}
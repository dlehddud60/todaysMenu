package com.example.todaysmenu.common.commonFile.service;

import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;

import java.util.List;

public interface CommonFileService {
    List<CommonFileDTO> list(CommonFileDTO commonFileDTO);
    int insert(CommonFileDTO commonFileDTO);
}

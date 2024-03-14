package com.example.todaysmenu.restFile.service;

import com.example.todaysmenu.restFile.DTO.RestFileDTO;
import com.example.todaysmenu.restFile.model.FindRequestFileListModel;
import com.example.todaysmenu.restFile.model.FindResponseFileListModel;

import java.util.List;

public interface RestFileService {
    List<FindResponseFileListModel> list(FindRequestFileListModel restFileDTO);
    int insert(RestFileDTO restFileDTO);
}

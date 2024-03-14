package com.example.todaysmenu.restFile.repository;

import com.example.todaysmenu.restFile.DTO.RestFileDTO;
import com.example.todaysmenu.restFile.model.FindRequestFileListModel;
import com.example.todaysmenu.restFile.model.FindResponseFileListModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestFileRepository {
    List<FindResponseFileListModel> list(FindRequestFileListModel findRequestFileListModel); //
    int insert(RestFileDTO restFileDTO);
    int update(RestFileDTO restFileDTO);
    int delete(int trft_parent_seq);

}

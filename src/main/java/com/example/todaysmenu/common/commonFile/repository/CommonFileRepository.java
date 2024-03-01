package com.example.todaysmenu.common.commonFile.repository;

import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonFileRepository {
    List<CommonFileDTO> list(CommonFileDTO commonFileDTO);
    int insert(CommonFileDTO commonFileDTO);
    int update(CommonFileDTO commonFileDTO);

}

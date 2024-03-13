package com.example.todaysmenu.common.commonFile.service.impl;

import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.repository.CommonFileRepository;
import com.example.todaysmenu.common.commonFile.service.CommonFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CommonFileServiceImpl implements CommonFileService {




    @Autowired
    CommonFileRepository commonFileRepository;


    @Override
    public List<CommonFileDTO> list(CommonFileDTO commonFileDTO) {
        return commonFileRepository.list(commonFileDTO);
    }

    @Override
    public int insert(CommonFileDTO commonFileDTO) {
        return commonFileRepository.insert(commonFileDTO);
    }

}


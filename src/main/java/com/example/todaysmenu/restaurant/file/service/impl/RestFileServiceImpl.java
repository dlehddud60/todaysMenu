package com.example.todaysmenu.restaurant.file.service.impl;

import com.example.todaysmenu.restaurant.file.entity.RestFileDTO;
import com.example.todaysmenu.restaurant.file.repository.RestFileRepository;
import com.example.todaysmenu.restaurant.file.service.RestFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RestFileServiceImpl implements RestFileService {




    @Autowired
    RestFileRepository restFileRepository;


    @Override
    public List<RestFileDTO> list(RestFileDTO restFileDTO) {
        return restFileRepository.list(restFileDTO);
    }

    @Override
    public int insert(RestFileDTO restFileDTO) {
        return restFileRepository.insert(restFileDTO);
    }

}


package com.example.todaysmenu.restFile.service.impl;

import com.example.todaysmenu.restFile.DTO.RestFileDTO;
import com.example.todaysmenu.restFile.repository.RestFileRepository;
import com.example.todaysmenu.restFile.service.RestFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RestFileServiceImpl implements RestFileService {

    private final RestFileRepository restFileRepository;

    @Override
    public List<RestFileDTO> list(RestFileDTO restFileDTO) {
        return restFileRepository.list(restFileDTO);
    }

    @Override
    public int insert(RestFileDTO restFileDTO) {
        return restFileRepository.insert(restFileDTO);
    }

}


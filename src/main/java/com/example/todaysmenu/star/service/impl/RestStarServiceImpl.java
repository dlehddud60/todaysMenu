package com.example.todaysmenu.star.service.impl;

import com.example.todaysmenu.pagination.DTO.Criteria;
import com.example.todaysmenu.star.DTO.RestStarDTO;
import com.example.todaysmenu.star.repository.RestStarRepository;
import com.example.todaysmenu.star.service.RestStarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RestStarServiceImpl implements RestStarService {

    private final RestStarRepository restStarRepository;

    @Override
    public int count(Criteria cri) {
        return restStarRepository.count(cri);
    }

    @Override
    public List<RestStarDTO> list(Criteria cri) {
        return restStarRepository.listPaging(cri);
    }

    @Override
    public RestStarDTO info(int trt_seq) {
        return restStarRepository.info(trt_seq);
    }

    @Override
    public int insert(RestStarDTO restStarDTO) {
       return restStarRepository.insert(restStarDTO);
    }

    @Override
    public int update(RestStarDTO restStarDTO) {
        return restStarRepository.update(restStarDTO);
    }

    @Override
    public int delete(int trst_seq) {
        return restStarRepository.delete(trst_seq);
    }

    @Override
    public int delete(RestStarDTO restStarDTO) {
        return restStarRepository.delete(restStarDTO);
    }
}

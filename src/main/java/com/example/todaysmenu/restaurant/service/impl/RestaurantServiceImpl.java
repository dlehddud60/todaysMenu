package com.example.todaysmenu.restaurant.service.impl;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.file.entity.RestFileDTO;
import com.example.todaysmenu.restaurant.file.repository.RestFileRepository;
import com.example.todaysmenu.restaurant.repository.RestaurantRepository;
import com.example.todaysmenu.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.todaysmenu.restaurant.file.util.RestFile.*;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestFileRepository restFileRepository;

    @Override
    public int count(Criteria cri) {
        return restaurantRepository.count(cri);
    }

    @Override
    public List<RestaurantDTO> list(Criteria cri) {
        return restaurantRepository.listPaging(cri);
    }

    @Override
    public RestaurantDTO info(RestaurantDTO restaurantDTO) {
        return restaurantRepository.info(restaurantDTO);
    }
    @Override
    public RestaurantDTO info(int trt_seq) {
         return restaurantRepository.info(trt_seq);
    }

    @Override
    public int insert(RestaurantDTO restaurantDTO) {
       return restaurantRepository.insert(restaurantDTO);
    }

    @Override
    public int insert(RestaurantDTO restaurantDTO, RestFileDTO restFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = restFileDTO.getTrft_multi_file().get(0).isEmpty();
        int result = restaurantRepository.insert(restaurantDTO);
        if(!fileResult) {
            restFileMethod(restaurantDTO,restFileDTO,restFileRepository,request);
        }
        return result;
    }

    @Override
    public int update(RestaurantDTO restaurantDTO) {
        return restaurantRepository.update(restaurantDTO);
    }
    @Override
    public int update(RestaurantDTO restaurantDTO, RestFileDTO restFileDTO, HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        boolean fileResult = restFileDTO.getTrft_multi_file().get(0).isEmpty();
        int result = restaurantRepository.update(restaurantDTO);
        if(!fileResult) {
            restFileMethod(restaurantDTO,restFileDTO,restFileRepository,request);
        }
        return result;
    }

    @Override
    public int delete(int trt_seq) {
        return restaurantRepository.delete(trt_seq);
    }

    @Override
    public int delete(int trt_seq, RestFileDTO restFileDTO) {
        restFileDelMethod(restFileDTO);
        return restaurantRepository.delete(trt_seq);

    }

    @Override
    public int updateCount(int trt_seq) {
        return restaurantRepository.updateCount(trt_seq);
    }
}

package com.example.todaysmenu.restaurant.menu.service.impl;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import com.example.todaysmenu.restaurant.menu.repository.RestMenuRepository;
import com.example.todaysmenu.restaurant.menu.service.RestMenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

import static com.example.todaysmenu.board.common.modal.ComModal.DANGER;
import static com.example.todaysmenu.board.common.modal.ComModal.redirect;

@Service
@Log4j2
public class RestMenuServiceImpl implements RestMenuService {

    @Autowired
    RestMenuRepository restMenuRepository;
    List<Integer> exceptSeq = new ArrayList<>();


    @Override
    public int count(Criteria cri) {
        return restMenuRepository.count(cri);
    }

    @Override
    public List<RestMenuDTO> list(RestMenuDTO restMenuDTO) {
        return restMenuRepository.list(restMenuDTO);
    }

    @Override
    public List<RestMenuDTO> listPaging(Criteria cri) {
        return restMenuRepository.listPaging(cri);
    }

    @Override
    public List<RestMenuDTO> rentMenuList(int trt_seq) {
        return restMenuRepository.rentMenuList(trt_seq);
    }

    @Override
    public List<RestMenuDTO> rentMenuList(RestMenuDTO restMenuDTO) {
        log.info("=============rentMenuListRestMenuDTO==============={}",restMenuDTO.getTrmt_seqArr());

        return restMenuRepository.rentMenuList(restMenuDTO);
    }

    @Override
    public int insert(RestMenuDTO restaurantDTO) {
        return restMenuRepository.insert(restaurantDTO);
    }

    @Override
    public int update(RestMenuDTO restaurantDTO) {
        return restMenuRepository.update(restaurantDTO);
    }

    @Override
    public int delete(int trmt_seq) {
        return restMenuRepository.delete(trmt_seq);
    }

    @Override
    public int parentDel(int trt_seq) {
        return restMenuRepository.parentDel(trt_seq);

    }

    @Override
    public RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        restMenuDTO.setTmt_login_id(memberSession.getTmt_login_id());

        log.info("=============getTrmt_seqArr==============={}",restMenuDTO.getTrmt_seqArr());
        log.info("=============getTrt_seqArr==============={}",restMenuDTO.getTrt_seqArr());

        RestMenuDTO restMenuDTOResult = restMenuRepository.userRecommendMenu(restMenuDTO);
        log.info("========================restMenuDTOResult1==========================={}",restMenuDTOResult);
        if(restMenuDTO.getStatus() == 0 && restMenuDTOResult == null) {
            log.info("====================if 1번 시작=========================");
            restMenuDTO.setStatus(1);
            log.info("====================if 1번 시작restMenuDTO========================={}",restMenuDTO);
            restMenuDTOResult = restMenuRepository.userRecommendMenu(restMenuDTO);
        }
        if(restMenuDTO.getStatus()  == 1 && restMenuDTOResult == null){
            log.info("====================if 2번 시작=========================");

            restMenuDTO.setStatus(2);
            log.info("====================if 2번 시작restMenuDTO========================={}",restMenuDTO);

            restMenuDTOResult = restMenuRepository.userRecommendMenu(restMenuDTO);

        }
        if(restMenuDTO.getStatus()  == 2 && restMenuDTOResult == null){
            log.info("====================if 3번 시작=========================");

            restMenuDTO.setStatus(3);
            log.info("====================if 3번 시작restMenuDTO========================={}",restMenuDTO);

            restMenuDTOResult = restMenuRepository.userRecommendMenu(restMenuDTO);

        }
        if(restMenuDTO.getStatus()  == 3 && restMenuDTOResult == null){
            log.info("====================if 4번 시작=========================");

            restMenuDTO.setStatus(4);
            log.info("====================if 4번 시작restMenuDTO========================={}",restMenuDTO);
            restMenuDTOResult = restMenuRepository.userRecommendMenu(restMenuDTO);

        }
        log.info("========================restMenuDTOResult2==========================={}",restMenuDTOResult);

        return restMenuDTOResult;
    }

    @Override
    public RestMenuDTO userRecommendMenu(RestMenuDTO restMenuDTO, HttpServletRequest request, RedirectAttributes rttr,Model model) {
        return null;
    }
}

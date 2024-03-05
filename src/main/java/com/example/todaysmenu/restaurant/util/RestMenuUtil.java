package com.example.todaysmenu.restaurant.util;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import com.example.todaysmenu.restaurant.menu.keyword.domain.Keyword;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.repository.KeywordRepository;
import com.example.todaysmenu.restaurant.menu.keyword.service.KeywordService;
import com.example.todaysmenu.restaurant.menu.service.RestMenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Log4j2
public abstract class RestMenuUtil {
    public static void restInsertMeth(RestaurantDTO restaurantDTO, RestMenuDTO restMenuDTO, Keyword keywor, List<LinkedHashMap<String , String>> keywordMap, HttpServletRequest request, MemberDTO memberSession, RestMenuService restMenuService, KeywordService keywordService) {
        List<String> insert = restMenuDTO.getTrmt_menu_nameArr();
        List<String> update = restMenuDTO.getTrmt_menu_nameArrUpdate();
        List<Integer> delete = restMenuDTO.getTrmt_seqArrDelete();
        List<String> keyWordInsertArr = keywor.getTrmkw_key_word();
        if(update != null) {
            for (int i = 0; i < update.size(); i++) {
                restMenuDTO.setTrmt_menu_name(restMenuDTO.getTrmt_menu_nameArrUpdate().get(i));
                restMenuDTO.setTrmt_price(restMenuDTO.getTrmt_priceArrUpdate().get(i));
                restMenuDTO.setTrmt_menu_text(restMenuDTO.getTrmt_menu_textArrUpdate().get(i));
                restMenuDTO.setTrt_seq(restaurantDTO.getTrt_seq());

                restMenuDTO.setTrmt_moder_ty(memberSession.getTmt_user_type());
                restMenuDTO.setTrmt_moder_nm(memberSession.getTmt_memb_name());
                restMenuDTO.setTrmt_moder_id(memberSession.getTmt_login_id());
                restMenuDTO.setTrmt_moder_ip(request.getRemoteAddr());
                restMenuService.update(restMenuDTO);
            }
        }
        if(delete != null) {
            for (int i = 0; i < delete.size(); i++) {
                restMenuDTO.setTrmt_seq(delete.get(i));
                int deleteSeq = restMenuDTO.getTrmt_seq();
                restMenuService.delete(deleteSeq);
            }
        }
        if(insert != null) {
            for (int i = 0; i < insert.size(); i++) {
                log.info("============restMenuDTO=============={}",restMenuDTO);
                restMenuDTO.setTrmt_menu_name(restMenuDTO.getTrmt_menu_nameArr().get(i)); ;
                restMenuDTO.setTrmt_price(restMenuDTO.getTrmt_priceArr().get(i)); ;
                restMenuDTO.setTrmt_menu_text(restMenuDTO.getTrmt_menu_textArr().get(i)); ;
                restMenuDTO.setTrt_seq(restaurantDTO.getTrt_seq());
                restMenuDTO.setTrmt_input_ty(memberSession.getTmt_user_type());
                restMenuDTO.setTrmt_input_nm(memberSession.getTmt_memb_name());
                restMenuDTO.setTrmt_input_id(memberSession.getTmt_login_id());
                restMenuDTO.setTrmt_input_ip(request.getRemoteAddr());
                restMenuDTO.setTrmt_moder_ty(memberSession.getTmt_user_type());
                restMenuDTO.setTrmt_moder_nm(memberSession.getTmt_memb_name());
                restMenuDTO.setTrmt_moder_id(memberSession.getTmt_login_id());
                restMenuDTO.setTrmt_moder_ip(request.getRemoteAddr());
                restMenuService.insert(restMenuDTO);
                log.info("===============restMenuDTO============={}",restMenuDTO.getTrmt_seq());
                log.info("===================keywor===================={}",keywor);
                log.info("===================keywordMap===================={}",keywordMap);

                for (int j = 0; j < keyWordInsertArr.size(); j++) {
                    log.info("================keyWordInsertArr================={}",j);
                    InsertRequsetKeywordModel keywordModel =  new InsertRequsetKeywordModel(restMenuDTO.getTrmt_seq(),keywor.getTrmkw_key_word().get(j),memberSession.getTmt_user_type(),memberSession.getTmt_memb_name(),memberSession.getTmt_login_id(),request.getRemoteAddr());
                    keywordService.insert(keywordModel);
                    log.info("==================keywordModel================{}",keywordModel);
                }
            }
        }

    }
}
package com.example.todaysmenu.restaurant.util;

import com.example.todaysmenu.member.DTO.MemberDTO;
import com.example.todaysmenu.restaurant.DTO.RestaurantDTO;
import com.example.todaysmenu.menu.DTO.RestMenuDTO;
import com.example.todaysmenu.keyword.DTO.Keyword;
import com.example.todaysmenu.keyword.model.DeleteRequsetKeywordModel;
import com.example.todaysmenu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.keyword.service.KeywordService;
import com.example.todaysmenu.menu.service.RestMenuService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public abstract class RestMenuUtil {
    public static void restInsertMeth(RestaurantDTO restaurantDTO, RestMenuDTO restMenuDTO, Keyword keyword, HttpServletRequest request, MemberDTO memberSession, RestMenuService restMenuService, KeywordService keywordService) {
        List<String> insert = restMenuDTO.getTrmt_menu_nameArr();
        List<String> update = restMenuDTO.getTrmt_menu_nameArrUpdate();
        List<Integer> delete = restMenuDTO.getTrmt_seqArrDelete();
        List<Keyword> keywordList = keyword.getList();
        if(update != null) {
            for (int i = 0; i < update.size(); i++) {
                restMenuDTO.setTrmt_menu_name(restMenuDTO.getTrmt_menu_nameArrUpdate().get(i));
                restMenuDTO.setTrmt_price(restMenuDTO.getTrmt_priceArrUpdate().get(i));
                restMenuDTO.setTrmt_menu_text(restMenuDTO.getTrmt_menu_textArrUpdate().get(i));
                restMenuDTO.setTrmt_seq(restMenuDTO.getTrmt_seqArr().get(i));
                restMenuDTO.setTrt_seq(restaurantDTO.getTrt_seq());
                restMenuDTO.setTrmt_moder_ty(memberSession.getTmt_user_type());
                restMenuDTO.setTrmt_moder_nm(memberSession.getTmt_memb_name());
                restMenuDTO.setTrmt_moder_id(memberSession.getTmt_login_id());
                restMenuDTO.setTrmt_moder_ip(request.getRemoteAddr());
                restMenuService.update(restMenuDTO);
                DeleteRequsetKeywordModel deleteRequsetKeywordModel = new DeleteRequsetKeywordModel(restMenuDTO.getTrmt_seq());
                keywordService.delete(deleteRequsetKeywordModel);
                keywordInsertMethod(restMenuDTO, request, memberSession, keywordService, keywordList, i);
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
                keywordInsertMethod(restMenuDTO, request, memberSession, keywordService, keywordList, i);
            }
        }

    }

    private static void keywordInsertMethod(RestMenuDTO restMenuDTO, HttpServletRequest request, MemberDTO memberSession, KeywordService keywordService, List<Keyword> keywordList, int i) {
        for (int j = 0; j < 1; j++) {
            List<String> trmkwKeyWord = keywordList.get(i).getTrmkw_key_word();
            for (int k = 0; k < trmkwKeyWord.size(); k++) {
                InsertRequsetKeywordModel keywordModel =  new InsertRequsetKeywordModel(restMenuDTO.getTrmt_seq(),trmkwKeyWord.get(k),memberSession.getTmt_user_type(),memberSession.getTmt_memb_name(),memberSession.getTmt_login_id(),request.getRemoteAddr());
            keywordService.insert(keywordModel);
            }
        }
    }
}

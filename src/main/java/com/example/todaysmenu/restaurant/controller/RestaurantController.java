package com.example.todaysmenu.restaurant.controller;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.pagination.entity.PageDTO;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.menu.entity.RestMenuDTO;
import com.example.todaysmenu.restaurant.menu.service.impl.RestMenuServiceImpl;
import com.example.todaysmenu.restaurant.service.RestaurantService;
import com.example.todaysmenu.restaurant.star.entity.RestStarDTO;
import com.example.todaysmenu.restaurant.star.service.RestStarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.*;


@Log4j2
@Controller
@RequestMapping("/restaurant/*")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    RestMenuServiceImpl restMenuService;
    @Autowired
    RestStarService restStarService;

    @GetMapping("/index.do")
    public String list(Criteria cri, Model model) {
        log.info("index.do 호출 cri{}",cri);
        int total = restaurantService.count(cri);
        model.addAttribute("list", restaurantService.list(cri));
        model.addAttribute("pageMaker",new PageDTO(total,cri));

        return "restaurant/list";
    }

    @GetMapping("/write.do")
    public String write(
            @RequestParam(value = "trt_seq",required = false)
            Integer trt_seq, Model model,RedirectAttributes rttr, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        RestaurantDTO restaurantDTO;
        RestaurantDTO restaurantDTOInfo = new RestaurantDTO();
        RestMenuDTO restMenuDTO = new RestMenuDTO();
        String memberWriter;
        String restaurant;
        try{
            restaurantDTOInfo.setTmt_login_id(memberSession.getTmt_login_id());
            restaurantDTOInfo.setTrt_seq(trt_seq);
            restaurantDTO = restaurantService.info(restaurantDTOInfo);
            memberWriter = memberSession.getTmt_memb_name();
            restaurant = restaurantDTO.getTrt_input_nm();
        } catch (NullPointerException e) {
            if(memberSession == null) {
                return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
            }else{
                return "restaurant/write";
            }
        }
        if(memberWriter.equals(restaurant) ) {
            log.info("test");
        }else{
            return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }
        if(memberSession == null) {
            return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }
        if(trt_seq != null){
            restMenuDTO.setTrt_seq(trt_seq);
            model.addAttribute("info",restaurantService.info(restaurantDTOInfo));
            model.addAttribute("menuList",restaurantService.info(restaurantDTOInfo));

            model.addAttribute("rentMenuList", restMenuService.rentMenuList(restMenuDTO));
            return "restaurant/update";
        }else{
            return "restaurant/write";
        }
    }

    @PostMapping("/proc.do")
    public String proc(@ModelAttribute RestaurantDTO restaurantDTO
                    ,  @ModelAttribute RestMenuDTO restMenuDTO
                    ,  @ModelAttribute Criteria cri
                    ,  RedirectAttributes rttr
                    ,  HttpServletRequest request) {
        int trt_seq = restaurantDTO.getTrt_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        restaurantDTO.setTrt_input_ty(memberSession.getTmt_user_type());
        restaurantDTO.setTrt_input_nm(memberSession.getTmt_memb_name());
        restaurantDTO.setTrt_input_id(memberSession.getTmt_login_id());
        restaurantDTO.setTrt_input_ip(request.getRemoteAddr());
        restaurantDTO.setTrt_moder_ty(memberSession.getTmt_user_type());
        restaurantDTO.setTrt_moder_nm(memberSession.getTmt_memb_name());
        restaurantDTO.setTrt_moder_id(memberSession.getTmt_login_id());
        restaurantDTO.setTrt_moder_ip(request.getRemoteAddr());
        log.info("=============procTrt_seq=============={}",trt_seq);
        if(trt_seq == 0){
           int dataSeq =  restaurantService.insert(restaurantDTO);
            restInsertMeth(restaurantDTO, restMenuDTO, request, memberSession);
            return redirect("restaurant/index.do",rttr,"성공 메세지","게시글 작성을 완료하였습니다.",SUCCESS);
        }else{
//            int dataSeq = restaurantDTO.getTrt_seq();
            String memberWriter = memberSession.getTmt_memb_name();
            RestaurantDTO restaurantDTOInfo = new RestaurantDTO();
            restaurantDTOInfo.setTmt_login_id(memberSession.getTmt_login_id());
            restaurantDTOInfo.setTrt_seq(trt_seq);

            restaurantService.info(restaurantDTOInfo);
            String restInputNm = restaurantDTO.getTrt_input_nm();
            if(memberWriter.equals(restInputNm)){
                restaurantService.update(restaurantDTO);
                restInsertMeth(restaurantDTO, restMenuDTO, request, memberSession);
            }else{
                return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
            }
            rttr.addFlashAttribute("result","success");
            rttr.addAttribute("pageNum",cri.getPageNum());
            rttr.addAttribute("amount",cri.getAmount());
        }
        return redirect("restaurant/index.do",rttr,"성공 메세지","수정을 완료하였습니다.",SUCCESS);
    }

    @GetMapping("/view.do")
    public String view(@ModelAttribute RestaurantDTO restaurantDTO,@ModelAttribute Criteria cri,  Model model, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        try{
            restaurantDTO.setTmt_login_id(memberSession.getTmt_login_id());
        }catch (NullPointerException e) {
            return redirect("",rttr,"실패 메세지","비로그인 유저는 진입하실 수 없습니다.",DANGER);
        }
        RestMenuDTO restMenuDTO = new RestMenuDTO();
        int trt_seq = restaurantDTO.getTrt_seq();
        restMenuDTO.setTrt_seq(trt_seq);
        restaurantService.updateCount(trt_seq);
//        log.info("==========restaurantDTO=========={}",restaㅌurantDTO);
        model.addAttribute("info", restaurantService.info(restaurantDTO));
        model.addAttribute("rentMenuList", restMenuService.rentMenuList(restMenuDTO));
        return "restaurant/view";
    }

    @GetMapping("/delete.do")
    public String delete(RestaurantDTO restaurantDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, HttpServletRequest request){
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        RestStarDTO restStarDTO = new RestStarDTO();
        int trt_seq = restaurantDTO.getTrt_seq();
        restStarDTO.setTrt_seq(trt_seq);
        restaurantDTO.setTmt_login_id(memberSession.getTmt_login_id());
        RestaurantDTO restaurantInfo;
        String memberWriter;
        String restaurant;
        try{
            restaurantInfo = restaurantService.info(restaurantDTO);
            memberWriter = memberSession.getTmt_memb_name();
            restaurant = restaurantInfo.getTrt_input_nm();
        } catch (NullPointerException e) {
            return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }
        if(memberWriter.equals(restaurant)){
            int dataSeq = restaurantService.delete(trt_seq); //식당 부모 게시글 삭제1
            restStarService.delete(restStarDTO);  //식당을 부모로 갖는 별점 삭제
            if(dataSeq == 1) {
                RestMenuDTO dataRestMenuDTO = new RestMenuDTO();
                dataRestMenuDTO.setTrt_seq(trt_seq);
                List<RestMenuDTO> restMenuDTO = restMenuService.rentMenuList(dataRestMenuDTO); //식당seq를 부모키로 갖는 메뉴리스트들 조회
                for (RestMenuDTO menuDTO : restMenuDTO) {
                    int trmt_seq_Arr = menuDTO.getTrmt_seq();
                    int menuDelResult = restMenuService.delete(trmt_seq_Arr); // for문으로 메뉴 리스트 삭제
                    if (menuDelResult > 0) {
                        RestStarDTO starDelDTO = new  RestStarDTO();
                        starDelDTO.setTrmt_seq(trmt_seq_Arr); //별점 삭제를 위한 dto객체 데이터 수동 매핑
                        restStarService.delete(starDelDTO); //메뉴 리스트 삭제 성공시 자식테이블, 메뉴 테이블을 부모키를 갖은 dto객체를 넘겨받아 부모키를 갖은 열들을 삭제

                    }
                }
            }
        }else{
            return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 삭제 가능합니다.",DANGER);
        }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("restaurant/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
    }

    @GetMapping("/delChk.do")
    public String delChk(@RequestParam(value = "trt_seq",required=false)List<Integer> trt_seq,Criteria cri,RedirectAttributes rttr,HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        RestaurantDTO restaurantInfo = new RestaurantDTO();
        String userSessionName = "";
        String statusMsg = "";
        String userName;
        try {
            userSessionName = memberSession.getTmt_memb_name();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
            for (int i = 0; i < trt_seq.size(); i++) {
                restaurantInfo.setTrt_seq(trt_seq.get(i));
                restaurantInfo.setTmt_login_id(memberSession.getTmt_login_id()); //서브쿼리용
                restaurantInfo = restaurantService.info(restaurantInfo);
                userName = restaurantInfo.getTrt_input_nm();
                if(userName.equals(userSessionName) && memberSession != null) {
                    delete(restaurantInfo, cri,rttr,request);
                } else {
                    if(memberSession == null) {
                        statusMsg = "로그인을 해주시길 바랍니다.";
                    }else {
                        statusMsg = "본인글만 수정 삭제 가능합니다.";
                    }
                    return redirect("restaurant/index.do",rttr,"실패 메세지",statusMsg,DANGER);
                }
            }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("restaurant/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
    }
    private void restInsertMeth(@ModelAttribute RestaurantDTO restaurantDTO, @ModelAttribute RestMenuDTO restMenuDTO, HttpServletRequest request, MemberDTO memberSession) {
        List<String> insert = restMenuDTO.getTrmt_menu_nameArr();
        List<String> update = restMenuDTO.getTrmt_menu_nameArrUpdate();
        List<Integer> delete = restMenuDTO.getTrmt_seqArrDelete();

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
            }
        }

    }
}

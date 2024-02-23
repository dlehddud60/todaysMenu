package com.example.todaysmenu.restaurant.controller;

import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.pagination.entity.PageDTO;
import com.example.todaysmenu.restaurant.entity.RestaurantDTO;
import com.example.todaysmenu.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.example.todaysmenu.board.common.modal.ComModal.*;


@Log4j2
@Controller
@RequestMapping("/restaurant/*")
public class RestaurantController {



    @Autowired
    RestaurantService restaurantService;


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
        String memberWriter;
        String restaurant;
        try{
            restaurantDTO = restaurantService.info(trt_seq);
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
            model.addAttribute("info",restaurantService.info(trt_seq));
            model.addAttribute("memberSession",memberSession);
            return "restaurant/update";
        }else{
            return "restaurant/write";
        }
    }

    @PostMapping("/proc.do")
    public String proc(RestaurantDTO restaurantDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, HttpServletRequest request) {
        int trt_seq = restaurantDTO.getTrt_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");


        restaurantDTO.setTrt_input_nm(memberSession.getTmt_memb_name());
        restaurantDTO.setTrt_input_ip(request.getRemoteAddr());
        restaurantDTO.setTrt_moder_ty(memberSession.getTmt_user_type());
        restaurantDTO.setTrt_moder_nm(memberSession.getTmt_memb_name());
        restaurantDTO.setTrt_moder_id(memberSession.getTmt_login_id());
        restaurantDTO.setTrt_moder_ip(request.getRemoteAddr());


        if(trt_seq == 0){
           int dataSeq =  restaurantService.insert(restaurantDTO);
            return redirect("restaurant/index.do",rttr,"성공 메세지","게시글 작성을 완료하였습니다.",SUCCESS);
        }else{
            int dataSeq = restaurantDTO.getTrt_seq();
            String memberWriter = memberSession.getTmt_memb_name();
            RestaurantDTO restaurantInfo = restaurantService.info(dataSeq);
            String restaurant = restaurantDTO.getTrt_input_nm();
            if(memberWriter.equals(restaurant)){
                restaurantService.update(restaurantDTO);

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
    public String view(@RequestParam int trt_seq, Model model, @ModelAttribute("cri") Criteria cri) {
        model.addAttribute("info", restaurantService.info(trt_seq));
        return "restaurant/view";
    }

    @GetMapping("/delete.do")
    public String  delete(RestaurantDTO restaurantDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, HttpServletRequest request){
        int trt_seq = restaurantDTO.getTrt_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");

        RestaurantDTO restaurantInfo;
        String memberWriter;
        String restaurant;
        try{
            restaurantInfo = restaurantService.info(trt_seq);
            memberWriter = memberSession.getTmt_memb_name();
            restaurant = restaurantInfo.getTrt_input_nm();
        } catch (NullPointerException e) {
            return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }


        if(memberWriter.equals(restaurant)){
            restaurantService.delete(trt_seq);

        }else{
            return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
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
        RestaurantDTO restaurantInfo;
        String userSessionName = "";
        String statusMsg = "";
        String userName;
        try {
            userSessionName = memberSession.getTmt_memb_name();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

            for (int i = 0; i < trt_seq.size(); i++) {
                restaurantInfo = restaurantService.info(trt_seq.get(i));
                userName = restaurantInfo.getTrt_input_nm();
                if(userName.equals(userSessionName) && memberSession != null) {
                restaurantService.delete(trt_seq.get(i));
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
        return "redirect:/restaurant/index.do";
    }



}

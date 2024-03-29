package com.example.todaysmenu.restaurant.controller;

import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.keyword.model.FindRequestRedirectKewordModel;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.pagination.VO.PageVO;
import com.example.todaysmenu.restFile.model.FindRequestFileListModel;
import com.example.todaysmenu.restaurant.DTO.RestaurantDTO;
import com.example.todaysmenu.restFile.DTO.RestFileDTO;
import com.example.todaysmenu.restFile.service.RestFileService;
import com.example.todaysmenu.menu.DTO.RestMenuDTO;
import com.example.todaysmenu.keyword.DTO.Keyword;
import com.example.todaysmenu.keyword.model.FindRequestKeywordListModel;
import com.example.todaysmenu.keyword.model.FindResponseKeywordListModel;
import com.example.todaysmenu.keyword.service.KeywordService;
import com.example.todaysmenu.menu.service.impl.RestMenuServiceImpl;
import com.example.todaysmenu.restaurant.service.RestaurantService;
import com.example.todaysmenu.star.DTO.RestStarDTO;
import com.example.todaysmenu.star.service.RestStarService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;

import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.*;
import static com.example.todaysmenu.restaurant.util.RestMenuUtil.*;


@Log4j2
@Controller
@RequestMapping("/restaurant/*")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestMenuServiceImpl restMenuService;
    private final RestStarService restStarService;
    private final RestFileService restFileService;
    private final KeywordService keywordService;

    @GetMapping("/index.do")
    public String list(Criteria cri, Model model) {
        int total = restaurantService.count(cri);
        model.addAttribute("list", restaurantService.list(cri));
        model.addAttribute("pageMaker",new PageVO(total,cri));
        return "restaurant/list";
    }

    @GetMapping("/write.do")
    public String write(
            @RequestParam(value = "trt_seq",required = false)
            Integer trt_seq, Model model,RedirectAttributes rttr, HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        RestaurantDTO restaurantDTO;
        RestaurantDTO restaurantDTOInfo = new RestaurantDTO();
        RestMenuDTO restMenuDTO = new RestMenuDTO();
        String loginId;
        String memberId;
        String memberType;
        try{
            restaurantDTOInfo.setTmt_login_id(memberSession.tmt_login_id());
            restaurantDTOInfo.setTrt_seq(trt_seq);
            restaurantDTO = restaurantService.info(restaurantDTOInfo);
            loginId = memberSession.tmt_login_id();
            memberType = memberSession.tmt_user_type();
            memberId = restaurantDTO.getTrt_input_id();

        } catch (NullPointerException e) {
            if(memberSession == null) {
                return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
            }else{
                return "restaurant/write";
            }
        }
        if(loginId.equals(memberId) || memberType.equals("master")) {
            log.info("test");
        }else{
            return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }
        if(memberSession == null) {
            return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }
        if(trt_seq != null){
            restMenuDTO.setTrt_seq(trt_seq);
            List<RestMenuDTO> restMenuDTOList = restMenuService.rentMenuList(restMenuDTO);
            for (int i = 0; i < restMenuDTOList.size(); i++) {
                int trmtSeq = restMenuDTOList.get(i).getTrmt_seq();
                FindRequestKeywordListModel findRequestKeywordListModel = new FindRequestKeywordListModel(trmtSeq);
                List<FindResponseKeywordListModel> keywordDetailList = keywordService.list(findRequestKeywordListModel);
                model.addAttribute("keywordList" + i,keywordDetailList);
            }
            model.addAttribute("info",restaurantService.info(restaurantDTOInfo));
            model.addAttribute("menuList",restaurantService.info(restaurantDTOInfo));
            model.addAttribute("rentMenuList", restMenuService.rentMenuList(restMenuDTO));
            return "restaurant/update";
        }else{
            return "restaurant/write";
        }
    }

    @PostMapping("/insert.do")
    public String insert(@ModelAttribute RestaurantDTO restaurantDTO
                    , @ModelAttribute RestMenuDTO restMenuDTO
                    , @ModelAttribute RestFileDTO restFileDTO
                    , @ModelAttribute Keyword keyword
                    , @ModelAttribute Criteria cri
            , RedirectAttributes rttr
            , HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        FindResponseLoginModel memberSession = getMemberDTO(restaurantDTO, request);
        try{
                restaurantService.insert(restaurantDTO,restFileDTO,request);
                restInsertMeth(restaurantDTO, restMenuDTO,keyword,request, memberSession,restMenuService,keywordService);
            }catch (Exception e) {
            List<String> insert = restMenuDTO.getTrmt_menu_nameArr();
            List<RestMenuDTO> restMenuLists = new ArrayList<>();
            try {
                for (int i = 0; i < insert.size(); i++) {
                    restMenuLists.add(new RestMenuDTO());
                    restMenuLists.get(i).setTrmt_menu_name(restMenuDTO.getTrmt_menu_nameArr().get(i));
                    restMenuLists.get(i).setTrmt_price(restMenuDTO.getTrmt_priceArr().get(i));
                    restMenuLists.get(i).setTrmt_menu_text(restMenuDTO.getTrmt_menu_textArr().get(i));
                }
            }catch (Exception ex) {
               log.info("Excepiton");
            }

            try{
                for (int i = 0; i < keyword.getList().size(); i++) {
                    List<FindRequestRedirectKewordModel> keywordDetailList = new ArrayList<>();
                    List<String> trmkwKeyWordList  = keyword.getList().get(i).getTrmkw_key_word();
                    for (int j = 0; j < trmkwKeyWordList.size(); j++) {
                        keywordDetailList.add(new FindRequestRedirectKewordModel(trmkwKeyWordList.get(j)));
                    }
                    rttr.addFlashAttribute("keywordList" + i,keywordDetailList);
                    keywordDetailList = null;
                }
            }catch (Exception ex) {
                log.info("Excepiton");
            }

            e.printStackTrace();
                rttr.addFlashAttribute("restaurantDTO",restaurantDTO);
                rttr.addFlashAttribute("restMenuList",restMenuLists);
                return redirect("restaurant/write.do",rttr,"실패 메시지","게시글을 전부 작성해주시길 바랍니다..",DANGER);
            }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("restaurant/index.do",rttr,"성공 메세지","게시글 작성을 완료하였습니다.",SUCCESS);
    }




    @PostMapping("/update.do")
    public String update(@ModelAttribute RestaurantDTO restaurantDTO
            , @ModelAttribute RestMenuDTO restMenuDTO
            , @ModelAttribute RestFileDTO restFileDTO
            , @ModelAttribute Keyword keyword
            , @ModelAttribute Criteria cri
            , RedirectAttributes rttr
            , HttpServletRequest request) throws FileExtensionExaption, FileSizeExaption {
        int trt_seq = restaurantDTO.getTrt_seq();
        FindResponseLoginModel memberSession = getMemberDTO(restaurantDTO, request);
        String memberId = memberSession.tmt_login_id();
        String memberType = memberSession.tmt_user_type();
        RestaurantDTO restaurantDTOInfo = new RestaurantDTO();
        restaurantDTOInfo.setTmt_login_id(memberSession.tmt_login_id());
        restaurantDTOInfo.setTrt_seq(trt_seq);
        restaurantService.info(restaurantDTOInfo);
        String restInputId = restaurantDTO.getTrt_input_id();
        if(memberId.equals(restInputId) || memberType.equals("master")){
            restaurantService.update(restaurantDTO,restFileDTO,request);
            restInsertMeth(restaurantDTO, restMenuDTO,keyword, request, memberSession,restMenuService,keywordService);
        }else{
            return redirect("restaurant/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("restaurant/index.do",rttr,"성공 메세지","수정을 완료하였습니다.",SUCCESS);
    }



    @GetMapping("/view.do")
    public String view(@ModelAttribute RestaurantDTO restaurantDTO,@ModelAttribute Criteria cri,  Model model, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        try{
            restaurantDTO.setTmt_login_id(memberSession.tmt_login_id());
        }catch (NullPointerException e) {
            return redirect("",rttr,"실패 메세지","비로그인 유저는 진입하실 수 없습니다.",DANGER);
        }
        RestMenuDTO restMenuDTO = new RestMenuDTO();
        int trt_seq = restaurantDTO.getTrt_seq();
        restMenuDTO.setTrt_seq(trt_seq);
        List<RestMenuDTO> restMenuDTOList = restMenuService.rentMenuList(restMenuDTO);
        for (int i = 0; i < restMenuDTOList.size(); i++) {
            int trmtSeq = restMenuDTOList.get(i).getTrmt_seq();
            FindRequestKeywordListModel findRequestKeywordListModel = new FindRequestKeywordListModel(trmtSeq);
            List<FindResponseKeywordListModel> keywordDetailList = keywordService.list(findRequestKeywordListModel);
            model.addAttribute("keywordList" + i,keywordDetailList);
        }
        restaurantService.updateCount(trt_seq);
        FindRequestFileListModel findRequestFileListModel = new FindRequestFileListModel(trt_seq);
        model.addAttribute("info", restaurantService.info(restaurantDTO));
        model.addAttribute("restFileList", restFileService.list(findRequestFileListModel));
        model.addAttribute("rentMenuList", restMenuDTOList);
        return "restaurant/view";
    }

    @GetMapping("/delete.do")
    public String delete(@ModelAttribute RestaurantDTO restaurantDTO,@ModelAttribute RestFileDTO restFileDTO,  @ModelAttribute Criteria cri, RedirectAttributes rttr, HttpServletRequest request){

        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        RestStarDTO restStarDTO = new RestStarDTO();
        int trt_seq = restaurantDTO.getTrt_seq();
        restStarDTO.setTrt_seq(trt_seq);
        restaurantDTO.setTmt_login_id(memberSession.tmt_login_id());
        RestaurantDTO restaurantInfo;
        String memberWriter;
        String restaurant;
        String memberType;
        try{
            restaurantInfo = restaurantService.info(restaurantDTO);
            memberWriter = memberSession.tmt_memb_name();
            restaurant = restaurantInfo.getTrt_input_nm();
            memberType = memberSession.tmt_user_type();

        } catch (NullPointerException e) {
            return redirect("restaurant/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }

        int dataSeq;
        if(memberWriter.equals(restaurant) || memberType.equals("master")){
            try {
                dataSeq = restaurantService.delete(trt_seq,restFileDTO); //식당 부모 게시글 삭제1

            }catch (NullPointerException e) {
                dataSeq = restaurantService.delete(trt_seq); //식당 부모 게시글 삭제1

            }
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


    private static FindResponseLoginModel getMemberDTO(RestaurantDTO restaurantDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        restaurantDTO.setTrt_input_ty(memberSession.tmt_user_type());
        restaurantDTO.setTrt_input_nm(memberSession.tmt_memb_name());
        restaurantDTO.setTrt_input_id(memberSession.tmt_login_id());
        restaurantDTO.setTrt_input_ip(request.getRemoteAddr());
        restaurantDTO.setTrt_moder_ty(memberSession.tmt_user_type());
        restaurantDTO.setTrt_moder_nm(memberSession.tmt_memb_name());
        restaurantDTO.setTrt_moder_id(memberSession.tmt_login_id());
        restaurantDTO.setTrt_moder_ip(request.getRemoteAddr());
        return memberSession;
    }

//    @GetMapping("/delChk.do")
//    public String delChk(@RequestParam(value = "trt_seq",required=false)List<Integer> trt_seq,Criteria cri,RedirectAttributes rttr,HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
//        RestaurantDTO restaurantInfo = new RestaurantDTO();
//        String userSessionName = "";
//        String statusMsg = "";
//        String userName;
//        try {
//            userSessionName = memberSession.tmt_memb_name();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//            for (int i = 0; i < trt_seq.size(); i++) {
//                restaurantInfo.setTrt_seq(trt_seq.get(i));
//                restaurantInfo.setTmt_login_id(memberSession.tmt_login_id()); //서브쿼리용
//                restaurantInfo = restaurantService.info(restaurantInfo);
//                userName = restaurantInfo.getTrt_input_nm();
//                if(userName.equals(userSessionName) && memberSession != null) {
//                    delete(restaurantInfo, cri,rttr,request);
//                } else {
//                    if(memberSession == null) {
//                        statusMsg = "로그인을 해주시길 바랍니다.";
//                    }else {
//                        statusMsg = "본인글만 수정 삭제 가능합니다.";
//                    }
//                    return redirect("restaurant/index.do",rttr,"실패 메세지",statusMsg,DANGER);
//                }
//            }
//        rttr.addFlashAttribute("result","success");
//        rttr.addAttribute("pageNum",cri.getPageNum());
//        rttr.addAttribute("amount",cri.getAmount());
//        return redirect("restaurant/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
//    }

}

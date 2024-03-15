package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.DTO.BoardDTO;
import com.example.todaysmenu.board.model.FindResponseBoardInfoModel;
import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import com.example.todaysmenu.boardFile.service.BoardFileService;
import com.example.todaysmenu.exception.FileExtensionExaption;
import com.example.todaysmenu.exception.FileSizeExaption;
import com.example.todaysmenu.member.model.FindResponseLoginModel;
import com.example.todaysmenu.pagination.VO.Criteria;
import com.example.todaysmenu.pagination.VO.PageVO;
import com.example.todaysmenu.board.service.BoardService;
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
@RequestMapping("/board/*")
public class BoardController {



    @Autowired
    BoardService boardService;

    @Autowired
    BoardFileService boardFileService;


    @GetMapping("/index.do")
    public String list(Criteria cri, Model model) {
        log.info("index.do 호출 cri{}",cri);
        int total = boardService.count(cri);
        model.addAttribute("list", boardService.list(cri));
        model.addAttribute("pageMaker",new PageVO(total,cri));

        return "board/list";
    }

    @GetMapping("/write.do")
    public String write(
            @RequestParam(value = "tfb_seq",required = false)
            Integer tfb_seq, Model model,RedirectAttributes rttr, HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        FindResponseBoardInfoModel boardInfoModel;
        String loginId;
        String memberId;
        String memberType;
        try{
            boardInfoModel = boardService.info(tfb_seq);
            loginId = memberSession.tmt_memb_name();
            memberId = boardInfoModel.tfb_input_nm();
            memberType = memberSession.tmt_user_type();
        } catch (NullPointerException e) {
            if(memberSession == null) {
                return redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
            }else{
                return "board/write";
            }
        }
        if(loginId.equals(memberId)|| memberType.equals("master") ) {
            log.info("test");
        }else{
            return redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }
        if(memberSession == null) {
            return redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }
        if(tfb_seq != null){
            model.addAttribute("info",boardService.info(tfb_seq));
            model.addAttribute("memberSession",memberSession);
            return "board/update";
        }else{
            return "board/write";
        }
    }

    @PostMapping("/insert.do")
    public String insert(@ModelAttribute BoardFileDTO boardFileDTO
            ,    @ModelAttribute BoardDTO boardDTO
            ,    @ModelAttribute Criteria cri
            ,    RedirectAttributes rttr
            ,    HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        int  tfb_seq = boardDTO.getTfb_seq();
        boardDTO.setTfb_input_ty(memberSession.tmt_user_type());
        boardDTO.setTfb_input_nm(memberSession.tmt_memb_name());
        boardDTO.setTfb_input_ip(request.getRemoteAddr());
        try {
            boardService.insert(boardDTO,boardFileDTO,request);
        } catch (FileExtensionExaption | FileSizeExaption e) {
            return redirect("",rttr,"실패",e.getMessage(),DANGER);
        }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("board/index.do",rttr,"성공 메세지","게시물을 작성하였습니다.",SUCCESS);
    }


    @PostMapping("/update.do")
    public String update(@ModelAttribute BoardFileDTO boardFileDTO
            ,    @ModelAttribute BoardDTO boardDTO
            ,    @ModelAttribute Criteria cri
            ,    RedirectAttributes rttr
            ,    HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        boardDTO.setTfb_moder_ty(memberSession.tmt_user_type());
        boardDTO.setTfb_moder_nm(memberSession.tmt_memb_name());
        boardDTO.setTfb_moder_ip(request.getRemoteAddr());
        int dataSeq = boardDTO.getTfb_seq();
        String memberWriter = memberSession.tmt_memb_name();
        FindResponseBoardInfoModel boardInfoModel = boardService.info(dataSeq);
        String boardWriter = boardInfoModel.tfb_input_nm();
        String memberType = memberSession.tmt_user_type();
        if(memberWriter.equals(boardWriter) || memberType.equals("master")){
            try {
                boardService.update(boardDTO,boardFileDTO,request);
            } catch (FileExtensionExaption | FileSizeExaption e) {
                return redirect("",rttr,"실패",e.getMessage(),DANGER);
            }
        }else{
            return redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("board/index.do",rttr,"성공 메세지","게시물을 수정하였습니다.",SUCCESS);
    }

    @GetMapping("/view.do")
    public String view(@RequestParam int tfb_seq, Model model, @ModelAttribute("cri") Criteria cri) {
        BoardFileDTO commonFileDTO = new BoardFileDTO();
        commonFileDTO.setTcft_parent_seq(tfb_seq);
        model.addAttribute("commonFileList",boardFileService.list(commonFileDTO));
        model.addAttribute("info", boardService.info(tfb_seq));
        return "board/view";
    }

    @GetMapping("/delete.do")
    public String  delete(@ModelAttribute BoardFileDTO boardFileDTO,@ModelAttribute BoardDTO boardDTO, @ModelAttribute Criteria cri, RedirectAttributes rttr, HttpServletRequest request){
        log.info("============Controller commonFileDTO========={}",boardFileDTO);
        int tfb_seq = boardDTO.getTfb_seq();
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");

        FindResponseBoardInfoModel boardInfoModel;
        String memberWriter;
        String boardWriter;
        String memberType;

        try{
            boardInfoModel = boardService.info(tfb_seq);
            memberWriter = memberSession.tmt_memb_name();
            boardWriter = boardInfoModel.tfb_input_nm();
            memberType = memberSession.tmt_user_type();

        } catch (NullPointerException e) {
            return redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }


        if(memberWriter.equals(boardWriter) || memberType.equals("master")){
            try {
                boardService.delete(tfb_seq,boardFileDTO);

            }catch (NullPointerException e) {
                boardService.delete(tfb_seq);

            }
        }else{
            return redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
        }

        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("board/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
    }

    @GetMapping("/delChk.do")
    public String delChk(@RequestParam(value = "tfb_seq",required=false)List<Integer> tfb_seq,Criteria cri,RedirectAttributes rttr,HttpServletRequest request) {
        HttpSession session = request.getSession();
        FindResponseLoginModel memberSession = (FindResponseLoginModel) session.getAttribute("memberDTO");
        FindResponseBoardInfoModel boardInfoModel;
        String userSessionName = "";
        String statusMsg = "";
        String userName;
        String memberType = "";
        try {
            userSessionName = memberSession.tmt_memb_name();
            memberType = memberSession.tmt_user_type();

        } catch (NullPointerException e) {
            log.info("세션 객체 없음");
        }

        for (int i = 0; i < tfb_seq.size(); i++) {
            boardInfoModel = boardService.info(tfb_seq.get(i));
            userName = boardInfoModel.tfb_input_nm();
            if(userName.equals(userSessionName) && memberSession != null || memberType.equals("master")) {
                boardService.delete(tfb_seq.get(i));
            } else {
                if(memberSession == null) {
                    statusMsg = "로그인을 해주시길 바랍니다.";
                }else {
                    statusMsg = "본인글만 수정 삭제 가능합니다.";

                }
                return redirect("board/index.do",rttr,"실패 메세지",statusMsg,DANGER);
            }
        }


        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return redirect("board/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
    }



}
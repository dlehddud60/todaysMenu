package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.service.CommonFileService;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.pagination.entity.PageDTO;
import com.example.todaysmenu.board.service.BoardService;
import com.example.todaysmenu.member.entity.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletionService;

import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.*;


@Log4j2
@Controller
@RequestMapping("/board/*")
public class BoardController {



    @Autowired
    BoardService boardService;

    @Autowired
    CommonFileService commonFileService;


    @GetMapping("/index.do")
    public String list(Criteria cri, Model model) {
        log.info("index.do 호출 cri{}",cri);
        int total = boardService.count(cri);
        model.addAttribute("list", boardService.list(cri));
        model.addAttribute("pageMaker",new PageDTO(total,cri));

        return "board/list";
    }

    @GetMapping("/write.do")
    public String write(
            @RequestParam(value = "tfb_seq",required = false)
            Integer tfb_seq, Model model,RedirectAttributes rttr, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        BoardDTO boardInfo;
        String memberWriter;
        String boardWriter;
        try{
            boardInfo = boardService.info(tfb_seq);
            memberWriter = memberSession.getTmt_memb_name();
            boardWriter = boardInfo.getTfb_input_nm();
        } catch (NullPointerException e) {
            if(memberSession == null) {
                return redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
            }else{
                return "board/write";
            }
        }
        if(memberWriter.equals(boardWriter) ) {
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

    @PostMapping("/proc.do")
    public String proc(@ModelAttribute CommonFileDTO commonFileDTO, @ModelAttribute BoardDTO boardDTO, @ModelAttribute Criteria cri,  RedirectAttributes rttr, HttpServletRequest request) {

        int  tfb_seq = boardDTO.getTfb_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");


        if(tfb_seq == 0){
            boardDTO.setTfb_input_ty(memberSession.getTmt_user_type());
            boardDTO.setTfb_input_nm(memberSession.getTmt_memb_name());
            boardDTO.setTfb_input_ip(request.getRemoteAddr());
            try {
                boardService.insert(boardDTO,commonFileDTO,request);
            } catch (FileExtensionExaption | FileSizeExaption e) {
                return redirect("",rttr,"실패",e.getMessage(),DANGER);

            }
            return redirect("board/index.do",rttr,"성공 메세지","게시물을 작성하였습니다.",SUCCESS);
        }else{
            boardDTO.setTfb_moder_ty(memberSession.getTmt_user_type());
            boardDTO.setTfb_moder_nm(memberSession.getTmt_memb_name());
            boardDTO.setTfb_moder_ip(request.getRemoteAddr());
            int dataSeq = boardDTO.getTfb_seq();
            String memberWriter = memberSession.getTmt_memb_name();
            BoardDTO boardInfo = boardService.info(dataSeq);
            String boardWriter = boardInfo.getTfb_input_nm();
            if(memberWriter.equals(boardWriter)){
                try {
                    boardService.update(boardDTO,commonFileDTO,request);
                } catch (FileExtensionExaption | FileSizeExaption e) {
                    return redirect("",rttr,"실패",e.getMessage(),DANGER);

                }
            }else{
                return redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",DANGER);
            }
            rttr.addFlashAttribute("result","success");
            rttr.addAttribute("pageNum",cri.getPageNum());
            rttr.addAttribute("amount",cri.getAmount());

        }
        return redirect("board/index.do",rttr,"성공 메세지","게시물을 수정하였습니다.",SUCCESS);
    }

    @GetMapping("/view.do")
    public String view(@RequestParam int tfb_seq, Model model, @ModelAttribute("cri") Criteria cri) {
        CommonFileDTO commonFileDTO = new CommonFileDTO();
        commonFileDTO.setTcft_parent_seq(tfb_seq);
        model.addAttribute("commonFileList",commonFileService.list(commonFileDTO));
        model.addAttribute("info", boardService.info(tfb_seq));
        return "board/view";
    }

    @GetMapping("/delete.do")
    public String  delete(@ModelAttribute CommonFileDTO commonFileDTO,@ModelAttribute BoardDTO boardDTO, @ModelAttribute Criteria cri, RedirectAttributes rttr, HttpServletRequest request){
        log.info("============Controller commonFileDTO========={}",commonFileDTO);
        int tfb_seq = boardDTO.getTfb_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");

        BoardDTO boardInfo;
        String memberWriter;
        String boardWriter;
        try{
            boardInfo = boardService.info(tfb_seq);
            memberWriter = memberSession.getTmt_memb_name();
            boardWriter = boardInfo.getTfb_input_nm();
        } catch (NullPointerException e) {
            return redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",DANGER);
        }


        if(memberWriter.equals(boardWriter)){
            try {
                boardService.delete(tfb_seq,commonFileDTO);

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
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        BoardDTO boardInfo;
        String userSessionName = "";
        String statusMsg = "";
        String userName;
        try {
            userSessionName = memberSession.getTmt_memb_name();
        } catch (NullPointerException e) {
            log.info("세션 객체 없음");
        }

            for (int i = 0; i < tfb_seq.size(); i++) {
                boardInfo = boardService.info(tfb_seq.get(i));
                userName = boardInfo.getTfb_input_nm();
                if(userName.equals(userSessionName) && memberSession != null) {
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
        return redirect("restaurant/index.do",rttr,"성공 메세지","게시물을 삭제하였습니다.",SUCCESS);
    }



}

package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.common.modal.ComModal;
import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.entity.Criteria;
import com.example.todaysmenu.board.entity.PageDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import com.example.todaysmenu.member.entity.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Log4j2
@Controller
@RequestMapping("/board/*")
public class BoardController {

    String danger = "btn btn-danger";
    String success = "btn btn-success";
    public ComModal comModal = new ComModal();

    @Autowired
    public BoardService boardService;


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
                return comModal.redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",danger);
            }else{
                return "board/write";
            }
        }
        if(memberWriter.equals(boardWriter) ) {
            log.info("test");
        }else{
            return comModal.redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",danger);
        }
        if(memberSession == null) {
            return comModal.redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",danger);
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
    public String proc(BoardDTO boardDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr,HttpServletRequest request) {

        String  tfb_seq = boardDTO.getTfb_seq();
        HttpSession session = request.getSession();
        MemberDTO memberSession = (MemberDTO) session.getAttribute("memberDTO");
        boardDTO.setTfb_input_nm(memberSession.getTmt_memb_name());
        boardDTO.setTfb_moder_nm(memberSession.getTmt_memb_name());
        if(tfb_seq == null){
            boardService.insert(boardDTO);
        }else{
            int dataSeq = Integer.parseInt(boardDTO.getTfb_seq());
            String memberWriter = memberSession.getTmt_memb_name();
            BoardDTO boardInfo = boardService.info(dataSeq);
            String boardWriter = boardInfo.getTfb_input_nm();
            if(memberWriter.equals(boardWriter)){
                boardService.update(boardDTO);
            }else{
                return comModal.redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",danger);
            }
            rttr.addFlashAttribute("result","success");
            rttr.addAttribute("pageNum",cri.getPageNum());
            rttr.addAttribute("amount",cri.getAmount());

        }
        return "redirect:/board/index.do";
    }

    @GetMapping("/view.do")
    public String view(@RequestParam int tfb_seq, Model model, @ModelAttribute("cri") Criteria cri) {
        model.addAttribute("info", boardService.info(tfb_seq));
        return "board/view";
    }

    @GetMapping("/delete.do")
    public String  delete(BoardDTO boardDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr, HttpServletRequest request){
        log.info("=======boardDTO========{}",boardDTO);
        int tfb_seq = Integer.parseInt(boardDTO.getTfb_seq());
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
            return comModal.redirect("board/index.do",rttr,"실패 메세지","로그인을 해주시길 바랍니다.",danger);
        }


        if(memberWriter.equals(boardWriter)){
            boardService.delete(tfb_seq);
        }else{
            return comModal.redirect("board/index.do",rttr,"실패 메세지","본인글만 수정 삭제 가능합니다.",danger);
        }

        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return "redirect:/board/index.do";
    }

    @GetMapping("/delChk.do")
    public String delChk(@RequestParam(value = "tfb_seq",required=false)List<Integer> tfb_seq,Criteria cri,RedirectAttributes rttr) {
        log.info("tfb_seq{}",tfb_seq);
        for (int i = 0; i < tfb_seq.size(); i++) {
            boardService.delete(tfb_seq.get(i));
        }
        rttr.addFlashAttribute("result","success");
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return "redirect:/board/index.do";
    }


    public String redirect(RedirectAttributes rttr, String p1, String p2, String p3) {
        rttr.addFlashAttribute("msgType",p1);
        rttr.addFlashAttribute("msg",p2);
        rttr.addFlashAttribute("result",p3);
        return "redirect:/board/index.do";
    }
}

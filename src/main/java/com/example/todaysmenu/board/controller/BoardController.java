package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.entity.Criteria;
import com.example.todaysmenu.board.entity.PageDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/board/*")
public class BoardController {

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
            Integer tfb_seq,Model model) {
        if(tfb_seq != null){
            model.addAttribute("info",boardService.info(tfb_seq));
            return "board/update";
        }else{
            return "board/write";
        }
    }

    @PostMapping("/proc.do")
    public String proc(BoardDTO boardDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("boardDTO : {}", boardDTO);
        String  tfb_seq = boardDTO.getTfb_seq();
        log.info("=======dataSeq========{}",tfb_seq);
        if(tfb_seq == null){
            boardService.insert(boardDTO);
        }else{
            boardService.update(boardDTO);
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
    public String  delete(BoardDTO boardDTO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("=======boardDTO========{}",boardDTO);
        int tfb_seq = Integer.parseInt(boardDTO.getTfb_seq());
        boardService.delete(tfb_seq);
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
}

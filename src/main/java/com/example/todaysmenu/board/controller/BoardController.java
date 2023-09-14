package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/board/*")
public class BoardController {

    @Autowired
    public BoardService boardService;




    @GetMapping("/index.do")
    public String list(Model model) {
        log.info("index.do 호출");
        List<BoardDTO> list = boardService.list();
        model.addAttribute("list",list);
        return "board/list";
    }

    @GetMapping("/write.do")
    public String write() {
        return "board/write";
    }

    @PostMapping("/proc.do")
    public String proc(BoardDTO boardDTO) {
        log.info("boardDTO : {}",boardDTO);
        boardService.insert(boardDTO);
        log.info("insert===========");
        return "redirect:/board/index.do";
    }

    @GetMapping("/view.do")
    public String view(@RequestParam int tfb_seq, Model model) {
        log.info("view.do invoked.===========");
        model.addAttribute("info",boardService.info(tfb_seq));
        return "board/view";
    }
}

package com.example.todaysmenu.board.controller;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
public class TestController {

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/")
    public String test(Model model) {
        List<BoardDTO> list = boardRepository.list();
        model.addAttribute("list",list);
        log.info("ㅎㅇ");
        return "test";
    }
}

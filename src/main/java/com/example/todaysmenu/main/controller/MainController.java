package com.example.todaysmenu.main.controller;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.service.BoardService;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.repository.file.MemFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class MainController {
    @Autowired
    MemFileRepository fileRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request,Model model) {
        return "home";
    }
}

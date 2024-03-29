package com.example.todaysmenu.main.controller;

import com.example.todaysmenu.memFile.repository.MemFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainController {
    @GetMapping("/")
    public String home(HttpServletRequest request,Model model) {
        return "home";
    }
}

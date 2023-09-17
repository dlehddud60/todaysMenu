package com.example.todaysmenu;

import com.example.todaysmenu.board.controller.BoardController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebMvcTest
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPaging() throws Exception {
        System.out.println(mockMvc.perform(
                MockMvcRequestBuilders.get("/board/index.do")
                        .param("pageNum","2")
                        .param("amount","50"))
                .andReturn().getModelAndView().getModelMap());
    }



}

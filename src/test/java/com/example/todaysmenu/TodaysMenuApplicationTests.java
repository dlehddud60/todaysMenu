package com.example.todaysmenu;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.entity.Criteria;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
@SpringBootTest
class TodaysMenuApplicationTests {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void contextLoads() {
    }

    @Test
    public void testPaging() {
        Criteria cri = new Criteria();
        //10개씩 3페이지
        cri.setPageNum(2);
        cri.setAmount(10);


        List<BoardDTO> list = boardRepository.listPaging(cri);
        list.forEach(boardDTO -> System.out.println(list));

    }


}

package com.example.todaysmenu.board.common.modal;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
public class ComModal {
    public String redirect(String path,RedirectAttributes rttr, String p1, String p2, String p3) {
        log.info("=========boardRedirect invoked=========");
        rttr.addFlashAttribute("msgType",p1);
        rttr.addFlashAttribute("msg",p2);
        rttr.addFlashAttribute("result",p3);
            return "redirect:/"+path;
    }
}

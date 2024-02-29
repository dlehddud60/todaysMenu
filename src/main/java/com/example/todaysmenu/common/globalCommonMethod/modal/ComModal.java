package com.example.todaysmenu.common.globalCommonMethod.modal;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
public abstract class ComModal {
    public static final String DANGER = "btn btn-danger";
    public static final String SUCCESS = "btn btn-success";

    public static String redirect(String path,RedirectAttributes rttr, String p1, String p2, String p3) {
        rttr.addFlashAttribute("msgType",p1);
        rttr.addFlashAttribute("msg",p2);
        rttr.addFlashAttribute("result",p3);
            return "redirect:/"+path;
    }
}

package com.example.todaysmenu.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private int tfb_seq;
    private String tfb_title;
    private String tfb_text;
    private String tfb_input_ty;
    private String tfb_input_nm;
    private String tfb_input_dt;
    private String tfb_input_ip;
    private String tfb_moder_ty;
    private String tfb_moder_nm;
    private String tfb_moder_dt;
    private String tfb_moder_ip;

}

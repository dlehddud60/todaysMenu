package com.example.todaysmenu.board.model;

public record FindResponseBoardInfoModel
        (       int tfb_seq
        ,       String tfb_title_nm
        ,       String tfb_text
        ,       String tfb_input_ty
        ,       String tfb_input_nm
        ,       String tfb_input_dt
        ,       String tfb_input_ip
        ,       String tfb_moder_ty
        ,       String tfb_moder_nm
        ,       String tfb_moder_dt
        ,       String tfb_moder_ip
        ) { }

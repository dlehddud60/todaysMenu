package com.example.todaysmenu.restFile.model;

public record FindResponseFileListModel
        (   int trft_seq
        ,   int trft_parent_seq
        ,   String trft_parent_table_type
        ,   String trft_origin_file_name
        ,   String trft_change_file_name
        ,   String trft_input_ty
        ,   String trft_input_nm
        ,   String trft_input_id
        ,   String trft_input_ip
        ,   String trft_input_dt
        ,   String trft_moder_ty
        ,   String trft_moder_nm
        ,   String trft_moder_id
        ,   String trft_moder_ip
        ,   String trft_moder_dt
        ) {
}

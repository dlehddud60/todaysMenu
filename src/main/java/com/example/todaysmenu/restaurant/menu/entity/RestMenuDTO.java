package com.example.todaysmenu.restaurant.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMenuDTO {

    private int trmt_seq;
    private int trt_seq;
    private int trmt_price;

    private String row_num;
    private String trmt_menu_name;
    private String trmt_menu_text;

    private String trmt_input_ty;
    private String trmt_input_nm;
    private String trmt_input_id;
    private String trmt_input_ip;
    private String trmt_input_dt;
    private String trmt_moder_ty;
    private String trmt_moder_nm;
    private String trmt_moder_id;
    private String trmt_moder_ip;
    private String trmt_moder_dt;
}

package com.example.todaysmenu.restaurant.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMenuDTO {

    private int trmt_seq;
    private int trt_seq;

    private List<Integer> trmt_priceArr;
    private List<String> trmt_menu_nameArr;
    private List<String> trmt_menu_textArr;

    private int trmt_price;
    private String trmt_menu_name;
    private String trmt_menu_text;

    private String row_num;

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

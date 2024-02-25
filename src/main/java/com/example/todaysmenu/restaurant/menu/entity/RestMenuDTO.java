package com.example.todaysmenu.restaurant.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestMenuDTO {

//  메뉴테이블 데이터
    private int trmt_seq;
    private int trt_seq;
    private String row_num;
    private String trmt_price_comma;
    private List<Integer> trmt_priceArr;
    private List<String> trmt_menu_nameArr;
    private List<String> trmt_menu_textArr;
    private int trmt_price;
    private String trmt_menu_name;
    private String trmt_menu_text;

//  메뉴테이블 입력자 정보
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

//  식당 테이블 데이터
    private String trt_rest_name;
    private String trt_regi;
    private String trt_addr;
    private String trt_food_type;

//  별점 테이블 데이터
    private String trst_star_score;
    private String trst_seq;
    private String trst_today_ymd;
    private String trst_avg_score;

}

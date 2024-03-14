package com.example.todaysmenu.menu.model;

public record FindResponseMenuListModel
        (   String row_num
        ,   int trt_seq
        ,   int trmt_seq
        ,   String trmt_menu_name
        ,   int trmt_price
        ,   String trmt_price_comma
        ,   String trmt_menu_text
        ,   String trt_rest_name
        ,   String trt_regi
        ,   String trt_addr
        ,   String trt_food_type
        ,   String trst_star_score
        ,   String trst_avg_score
        ,   String trst_seq
        ,   String trst_today_ymd
        ) {
}

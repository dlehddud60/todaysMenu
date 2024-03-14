package com.example.todaysmenu.menu.model;

public record FindResponseSubMenuListModel
        (   Integer trmt_seq
        ,   Integer trt_seq
        ,   String trmt_menu_name
        ,   String trmt_price_comma
        ,   String trt_rest_name
        ,   String trt_addr
        ,   String trst_star_score
        ,   Integer  trst_menu_avg_score
        ,   Integer trst_rest_avg_score
        ,   Integer today_eat_trst_seq
        ,   String trst_today_ymd
        ) {
}

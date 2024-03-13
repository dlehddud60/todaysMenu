package com.example.todaysmenu.star.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestStarDTO {
    private String row_num;

    private int trst_seq;
    private int trt_seq;
    private int trmt_seq;


    private String trst_parent_seq;
    private String trst_parent_type;
    private String trst_star_score;
    private String trst_today_ymd;

    private String trst_input_ty;
    private String trst_input_nm;
    private String trst_input_id;
    private String trst_input_ip;
    private String trst_input_dt;
    private String trst_moder_ty;
    private String trst_moder_nm;
    private String trst_moder_id;
    private String trst_moder_ip;
    private String trst_moder_dt;
}

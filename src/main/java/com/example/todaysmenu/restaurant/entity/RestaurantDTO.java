package com.example.todaysmenu.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private int trt_seq;
    private String trt_count;
    private String row_num;
    private String trt_rest_name;
    private String trt_regi;
    private String trt_addr;
    private String trt_food_type;
    private String trt_rest_text;

    private String trt_input_ty;
    private String trt_input_nm;
    private String trt_input_id;
    private String trt_input_ip;
    private String trt_input_dt;
    private String trt_moder_ty;
    private String trt_moder_nm;
    private String trt_moder_id;
    private String trt_moder_ip;
    private String trt_moder_dt;
}

package com.example.todaysmenu.pagination.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
    private int pageNum;
    private int amount;
    private String search;
    private String gender;
    private String startDate;
    private String endDate;
    private String userType;
    private String trt_regiSh;
    private String trt_food_typeSh;

    public Criteria() {
        this(1,10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

}

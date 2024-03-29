package com.example.todaysmenu.menu.DTO;

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
    private int menu_row_num;
    private int trt_seq;
    private String row_num;
    private String trmt_price_comma;
    private List<Integer> trt_seqArr; //시퀀스를 배열로 받기 위함
    //    메뉴 리스트 같은 name값을 가진 객체들을 배열로 받기 위한 필드
    private List<Integer> trmt_priceArr;
    private List<String> trmt_menu_nameArr;
    private List<String> trmt_menu_textArr;
    private List<Integer> trmt_seqArr;

// 메뉴리스트 수정용 스테이터스값이 있는 필드
    private List<Integer> trmt_priceArrUpdate;
    private List<String> trmt_menu_nameArrUpdate;;
    private List<String> trmt_menu_textArrUpdate;
    private List<Integer> trmt_seqArrDelete;

// 메뉴리스트 제거용 스테이터스값이 있는 필드
    private List<Integer> trmt_priceArrDelete;
    private List<String> trmt_menu_nameArrDelete; ;
    private List<String> trmt_menu_textArrDelete;

//  사용자 아이디
    private String tmt_login_id;



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

//   추천 순서 숫자
    private int recomNum;
    private int trst_menu_avg_score;
    private int trst_rest_avg_score;

//  추천 로직 상태값
    private int status = 0;


    private String keywordSh;


}

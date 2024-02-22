package com.example.todaysmenu.member.entity;

import lombok.Data;

@Data

public class MemberDTO {
    private String row_num;
    private String  tmt_seq;
    private String tmt_login_id;
    private String tmt_pass_word;
    private String tmt_memb_name;
    private int tmt_memb_age;
    private String tmt_memb_gender;
    private String tmt_memb_email;
    private String tmt_memb_birth_day;
    private String tmt_memb_file;
    private String tmt_input_date;
    private String tmt_user_type;
    private String tmt_user_type_custom;
}

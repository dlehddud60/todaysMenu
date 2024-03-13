package com.example.todaysmenu.member.model;

public record FindResponseMemberListModel
        (   String row_num
        ,   String tmt_seq
        ,   String tmt_login_id
        ,   String tmt_pass_word
        ,   String tmt_memb_name
        ,   String tmt_memb_age
        ,   String tmt_memb_gender
        ,   String tmt_memb_email
        ,   String tmt_user_type
        ,   String tmt_memb_birth_day
        ,   String tmt_memb_file
        ,   String tmt_input_date
        ,   String tmt_user_type_custom
        ) {}

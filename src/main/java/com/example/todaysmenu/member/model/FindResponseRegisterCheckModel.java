package com.example.todaysmenu.member.model;

public record FindResponseRegisterCheckModel
        (   String tmt_seq
        ,   String tmt_login_id
        ,   String tmt_pass_word
        ,   String tmt_memb_name
        ,   int tmt_memb_age
        ,   String tmt_memb_gender
        ,   String tmt_memb_email
        ,   String tmt_memb_birth_day
        ,   String tmt_memb_file
        ,   String tmt_input_date

) {
}

package com.example.todaysmenu.restFile.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RestFileDTO {

    public RestFileDTO() {
    }




    private int trft_seq;
    private int trft_parent_seq;
    private String trft_parent_table_type;
    private String trft_origin_file_name;
    private String trft_change_file_name;
    private List<String>trft_change_file_nameArr;

    private String trft_input_ty;
    private String trft_input_nm;
    private String trft_input_id;
    private String trft_input_ip;
    private String trft_input_dt;
    private String trft_moder_ty;
    private String trft_moder_nm;
    private String trft_moder_id;
    private String trft_moder_ip;
    private String trft_moder_dt;

    private List<MultipartFile> trft_multi_file;//파일 자체를 담음

}

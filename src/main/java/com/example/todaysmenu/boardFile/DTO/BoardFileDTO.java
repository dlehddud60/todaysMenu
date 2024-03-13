package com.example.todaysmenu.boardFile.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardFileDTO {

    private int tcft_seq;
    private int tcft_parent_seq;
    private String tcft_parent_table_type;
    private String tcft_origin_file_name;
    private String tcft_change_fine_name;
    private List<String>tcft_change_fine_nameArr;

    private String tcft_input_ty;
    private String tcft_input_nm;
    private String tcft_input_id;
    private String tcft_input_ip;
    private String tcft_input_dt;
    private String tcft_moder_ty;
    private String tcft_moder_nm;
    private String tcft_moder_id;
    private String tcft_moder_ip;
    private String tcft_moder_dt;

    private List<MultipartFile> tcft_multi_file;//파일 자체를 담음

}

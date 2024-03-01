package com.example.todaysmenu.member.memFile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Data
public class MemFileDTO {

    public MemFileDTO() {
    }

    public MemFileDTO(int tmt_seq) {
        this.tmt_seq = tmt_seq;
    }

    private int tmt_seq;
    private int tmft_seq;
    private int tmft_parent_seq;
    private String tmft_origin_file_name;
    private String tmft_change_fine_name;


    private String tmft_input_ty;
    private String tmft_input_nm;
    private String tmft_input_id;
    private String tmft_input_ip;
    private String tmft_input_dt;
    private String tmft_moder_ty;
    private String tmft_moder_nm;
    private String tmft_moder_id;
    private String tmft_moder_ip;
    private String tmft_moder_dt;

    private int tmt_file_attached;
    private MultipartFile tmt_multi_file;//파일 자체를 담음


}

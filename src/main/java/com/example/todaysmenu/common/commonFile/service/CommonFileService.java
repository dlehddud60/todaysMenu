package com.example.todaysmenu.common.commonFile.service;

import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.memFile.entity.MemFileDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface CommonFileService {
    List<CommonFileDTO> list(CommonFileDTO commonFileDTO);
    int insert(CommonFileDTO commonFileDTO);
}

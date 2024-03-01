package com.example.todaysmenu.common.commonFile.service.impl;

import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.repository.CommonFileRepository;
import com.example.todaysmenu.common.commonFile.service.CommonFileService;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.member.entity.MemberDTO;
import com.example.todaysmenu.member.memFile.entity.MemFileDTO;
import com.example.todaysmenu.member.memFile.repository.MemFileRepository;
import com.example.todaysmenu.member.repository.MemberRepository;
import com.example.todaysmenu.pagination.entity.Criteria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.todaysmenu.common.globalCommonMethod.modal.ComModal.*;

@Service
@Log4j2
public class CommonFileServiceImpl implements CommonFileService {




    @Autowired
    CommonFileRepository commonFileRepository;


    @Override
    public List<CommonFileDTO> list(CommonFileDTO commonFileDTO) {
        return commonFileRepository.list(commonFileDTO);
    }

    @Override
    public int insert(CommonFileDTO commonFileDTO) {
        return commonFileRepository.insert(commonFileDTO);
    }

}


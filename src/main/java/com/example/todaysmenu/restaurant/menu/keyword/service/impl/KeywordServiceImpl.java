package com.example.todaysmenu.restaurant.menu.keyword.service.impl;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.board.repository.BoardRepository;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.commonFile.repository.CommonFileRepository;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.keyword.model.FindKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.UpdateRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.repository.KeywordRepository;
import com.example.todaysmenu.restaurant.menu.keyword.service.KeywordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.todaysmenu.common.commonFile.util.CommonFile.commonFileDelMethod;
import static com.example.todaysmenu.common.commonFile.util.CommonFile.commonFileMethod;

@Service
@Log4j2
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Override
    public List<FindKeywordListModel> list(FindKeywordListModel findKeywordListModel) {
        return keywordRepository.list(findKeywordListModel);
    }

    @Override
    public int insert(InsertRequsetKeywordModel insertRequsetKeywordModel) {
        return keywordRepository.insert(insertRequsetKeywordModel);
    }

    @Override
    public int update(UpdateRequsetKeywordModel updateRequsetKeywordModel) {
        return keywordRepository.update(updateRequsetKeywordModel);
    }
}

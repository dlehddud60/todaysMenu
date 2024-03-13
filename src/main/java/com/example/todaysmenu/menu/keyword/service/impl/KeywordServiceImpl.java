package com.example.todaysmenu.menu.keyword.service.impl;

import com.example.todaysmenu.menu.keyword.model.*;
import com.example.todaysmenu.menu.keyword.repository.KeywordRepository;
import com.example.todaysmenu.menu.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Override
    public List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel) {
        return keywordRepository.list(findRequestKeywordListModel);
    }

    @Override
    public int insert(InsertRequsetKeywordModel insertRequsetKeywordModel) {
        return keywordRepository.insert(insertRequsetKeywordModel);
    }

    @Override
    public int update(UpdateRequsetKeywordModel updateRequsetKeywordModel) {
        return keywordRepository.update(updateRequsetKeywordModel);
    }

    @Override
    public int delete(DeleteRequsetKeywordModel deleteRequsetKeywordModel) {
        return keywordRepository.delete(deleteRequsetKeywordModel);
    }
}

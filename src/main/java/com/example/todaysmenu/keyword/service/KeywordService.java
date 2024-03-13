package com.example.todaysmenu.keyword.service;

import com.example.todaysmenu.keyword.model.*;

import java.util.List;

public interface KeywordService {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
    int delete(DeleteRequsetKeywordModel deleteRequsetKeywordModel);


}

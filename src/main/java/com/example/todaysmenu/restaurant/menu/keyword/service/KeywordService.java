package com.example.todaysmenu.restaurant.menu.keyword.service;

import com.example.todaysmenu.restaurant.menu.keyword.model.*;

import java.util.List;

public interface KeywordService {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
    int delete(DeleteRequsetKeywordModel deleteRequsetKeywordModel);


}

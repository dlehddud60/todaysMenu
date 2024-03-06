package com.example.todaysmenu.restaurant.menu.keyword.service;

import com.example.todaysmenu.restaurant.menu.keyword.model.FindRequestKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.FindResponseKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.UpdateRequsetKeywordModel;

import java.util.List;

public interface KeywordService {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);

}

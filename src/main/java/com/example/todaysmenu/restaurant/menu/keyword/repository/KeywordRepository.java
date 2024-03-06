package com.example.todaysmenu.restaurant.menu.keyword.repository;

import com.example.todaysmenu.restaurant.menu.keyword.model.FindRequestKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.FindResponseKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.UpdateRequsetKeywordModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordRepository {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
}

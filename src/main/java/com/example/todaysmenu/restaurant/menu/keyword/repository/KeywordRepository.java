package com.example.todaysmenu.restaurant.menu.keyword.repository;

import com.example.todaysmenu.restaurant.menu.keyword.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordRepository {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
    int delete(DeleteRequsetKeywordModel deleteRequsetKeywordModel);

}

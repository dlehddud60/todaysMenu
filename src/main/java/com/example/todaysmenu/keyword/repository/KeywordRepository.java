package com.example.todaysmenu.keyword.repository;

import com.example.todaysmenu.keyword.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordRepository {
    List<FindResponseKeywordListModel> list(FindRequestKeywordListModel findRequestKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
    int delete(DeleteRequsetKeywordModel deleteRequsetKeywordModel);

}

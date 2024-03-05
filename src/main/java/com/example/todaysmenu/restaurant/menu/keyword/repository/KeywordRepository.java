package com.example.todaysmenu.restaurant.menu.keyword.repository;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.keyword.model.FindKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.UpdateRequsetKeywordModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeywordRepository {
    List<FindKeywordListModel> list(FindKeywordListModel findKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);
}

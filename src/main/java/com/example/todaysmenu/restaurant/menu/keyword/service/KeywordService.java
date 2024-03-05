package com.example.todaysmenu.restaurant.menu.keyword.service;

import com.example.todaysmenu.board.entity.BoardDTO;
import com.example.todaysmenu.common.commonFile.entity.CommonFileDTO;
import com.example.todaysmenu.common.customExaption.FileExtensionExaption;
import com.example.todaysmenu.common.customExaption.FileSizeExaption;
import com.example.todaysmenu.pagination.entity.Criteria;
import com.example.todaysmenu.restaurant.menu.keyword.model.FindKeywordListModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.InsertRequsetKeywordModel;
import com.example.todaysmenu.restaurant.menu.keyword.model.UpdateRequsetKeywordModel;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface KeywordService {
    List<FindKeywordListModel> list(FindKeywordListModel findKeywordListModel);
    int insert(InsertRequsetKeywordModel insertRequsetKeywordModel);
    int update(UpdateRequsetKeywordModel updateRequsetKeywordModel);

}

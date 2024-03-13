package com.example.todaysmenu.boardFile.repository;

import com.example.todaysmenu.boardFile.DTO.BoardFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardFileRepository {
    List<BoardFileDTO> list(BoardFileDTO boardFileDTO);
    int insert(BoardFileDTO boardFileDTO);
    int update(BoardFileDTO boardFileDTO);
    int delete(int tcft_parent_seq);

}

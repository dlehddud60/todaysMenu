package com.example.todaysmenu.pagination.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
    //amount 페이지당 데이터 수

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria cri;

    public PageDTO(int total, Criteria cri) {
        this.total = total;
        this.cri = cri;
        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10; //페이징 끝 번호 계산
        this.startPage = this.endPage - 9; //페이징 시작번호 계산

        //total을 통한 endPage계산----------------
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount())); //진짜 끝 페이지가 몆 번인지 계산

        if (realEnd < this.endPage) {
            this.endPage = realEnd;
        }//end
        //--------------------------------------
        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}

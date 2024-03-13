package com.example.todaysmenu.menu.keyword.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Keyword {
    List<String> trmkw_key_word;
    List<Keyword> list;

}

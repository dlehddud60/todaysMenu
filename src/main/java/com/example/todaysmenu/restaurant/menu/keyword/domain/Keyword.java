package com.example.todaysmenu.restaurant.menu.keyword.domain;

import jakarta.validation.constraints.NotEmpty;
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

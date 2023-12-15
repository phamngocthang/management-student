package com.tranning.management.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchByKeywordDto {
    private String keyword = "";
    private int pageIndex;
    private int pageSize;
}

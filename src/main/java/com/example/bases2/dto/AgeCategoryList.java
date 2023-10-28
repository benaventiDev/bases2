package com.example.bases2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AgeCategoryList extends Dto{
    List<AgeCategoryCount> edades;

}

package com.example.bases2.dto;

import com.example.bases2.repository.mongo_repository.EdadCount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class EdadesList extends Dto{
    private Map<Integer, Long> edades;

}

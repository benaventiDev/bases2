package com.example.bases2.dto;

import com.example.bases2.repository.mongo_repository.GeneroCount;
import com.example.bases2.repository.mongo_repository.RoomCount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
public class GeneroCountList extends Dto{
    private Map<String, Long> generos;

}
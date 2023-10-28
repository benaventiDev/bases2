package com.example.bases2.dto;

import com.example.bases2.repository.mongo_repository.RoomCount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RoomCountList extends Dto{
    private List<RoomCount> habitaciones;

}

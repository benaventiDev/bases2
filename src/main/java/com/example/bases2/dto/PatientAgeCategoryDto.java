package com.example.bases2.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PatientAgeCategoryDto extends Dto{

    private long below18;
    private long between18to60;
    private long above60;

    public PatientAgeCategoryDto(long below18, long between18to60, long above60) {
        this.below18 = below18;
        this.between18to60 = between18to60;
        this.above60 = above60;
    }

    // getters and setters
}

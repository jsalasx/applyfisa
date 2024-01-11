package com.drk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NumberOfTripsRequestDTO {
    private String init;
    private String end;
    private int minStops;
    private int maxStops;
}



package com.drk.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TripDTO {

    private String idTrip;
    private int numberOfTrip;
    private String trip;
    private String initNextTrip;
    private int distance;
}

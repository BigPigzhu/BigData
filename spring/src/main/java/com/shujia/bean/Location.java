package com.shujia.bean;

import lombok.*;

@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Location {
    private Double x;
    private Double y;
    private String startDate;
    private String endDate;
}

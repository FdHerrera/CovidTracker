package com.coronavirus.worldtracker.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LocationStats {
    private String country;
    private String state;
    private int newCasesReported;
    private int latestTotalCases;
    private int totalReportedCases;
    private List<LocationStats> allStats;
}

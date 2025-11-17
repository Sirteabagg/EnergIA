package com.example.api.dto;

import com.example.api.dao.MeasureDAO;

import java.util.List;

public class BuildingAvgPeriodDTO {

    private String startDate;
    private String endDate;
    private List<MeasureDAO.AverageConsumption> averageConsumption;

    public BuildingAvgPeriodDTO(String startDate, String endDate, List<MeasureDAO.AverageConsumption> averageConsumption) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageConsumption = averageConsumption;
    }

    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public List<MeasureDAO.AverageConsumption> getAverageConsumption() { return averageConsumption; }


}

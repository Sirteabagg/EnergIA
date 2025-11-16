package com.example.api.dto;

import java.util.List;
import com.example.api.dao.MeasureDAO;

public class SummaryDTO {
    private int buildings;
    private int sensors;
    private int anomalies;
    private float average;
    private float std;

    private List<MeasureDAO.AverageConsumption> averages;

    public SummaryDTO() {}

    public SummaryDTO(int buildings, int sensors, int anomalies, List<MeasureDAO.AverageConsumption> averages) {
        this.buildings = buildings;
        this.sensors = sensors;
        this.anomalies = anomalies;
        this.averages = averages;
    }

    // Getters
    public int getBuildings() { return buildings; }
    public int getSensors() { return sensors; }
    public int getAnomalies() { return anomalies; }
    public List<MeasureDAO.AverageConsumption> getAverages() { return averages; }
}

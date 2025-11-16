package com.example.api.dto;

import java.util.List;
import com.example.api.dao.MeasureDAO;

public class SummaryDTO {
    private int buildings;
    private int sensors;
    private int anomalies;
    private float averageHumidityGlobal;
    private float averageTemperatureGlobal;
    private float averagePowerConsumptionGlobal;

    private List<BuildingAverageDTO> buildingAverages;

    private List<MeasureDAO.AverageConsumption> averages;

    public SummaryDTO(int buildings, int sensors, int anomalies, float averageHumidityGlobal,
                      float averageTemperatureGlobal, float averagePowerConsumptionGlobal,
                      List<BuildingAverageDTO> buildingAverages) {
        this.buildings = buildings;
        this.sensors = sensors;
        this.anomalies = anomalies;
        this.averageHumidityGlobal = averageHumidityGlobal;
        this.averageTemperatureGlobal = averageTemperatureGlobal;
        this.averagePowerConsumptionGlobal = averagePowerConsumptionGlobal;
        this.buildingAverages = buildingAverages;
    }

    // Getters
    public int getBuildings() { return buildings; }
    public int getSensors() { return sensors; }
    public int getAnomalies() { return anomalies; }
    public float getAverageHumidityGlobal() { return averageHumidityGlobal; }
    public float getAverageTemperatureGlobal() { return averageTemperatureGlobal; }
    public float getAveragePowerConsumptionGlobal() { return averagePowerConsumptionGlobal; }
    public List<BuildingAverageDTO> getAverages() { return buildingAverages; }
}

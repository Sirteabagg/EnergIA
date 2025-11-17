package com.example.api.dto;

public class BuildingAverageDTO {
    private String buildingName;
    private float averageHumidity;
    private float averageTemperature;
    private float averagePowerConsumption;

    public BuildingAverageDTO(String buildingName, float averageHumidity, float averageTemperature, float averagePowerConsumption) {
        this.buildingName = buildingName;
        this.averageHumidity = averageHumidity;
        this.averageTemperature = averageTemperature;
        this.averagePowerConsumption = averagePowerConsumption;
    }

    public String getBuildingName() { return buildingName; }
    public float getAverageHumidity() { return averageHumidity; }
    public float getAverageTemperature() { return averageTemperature; }
    public float getAveragePowerConsumption() { return averagePowerConsumption; }
}


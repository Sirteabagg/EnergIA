package com.example.api.model;

public class Measure {
    private int id;
    private String timestamp;
    private float humidity;
    private float temperature;
    private float power_consumption;
    private int building_id;
    private int sensor_id;

    public Measure(int id, String timestamp, float humidity, float temperature, float power_consumption, int building_id, int sensor_id) {
        this.id = id;
        this.timestamp = timestamp;
        this.humidity = humidity;
        this.temperature = temperature;
        this.power_consumption = power_consumption;
        this.building_id = building_id;
        this.sensor_id = sensor_id;
    }

    public int getId() { return id; }
    public String getTimestamp() { return timestamp; }
    public float getHumidity() { return humidity; }
    public float getTemperature() { return temperature; }
    public float getPowerConsumption() { return power_consumption; }
    public int getBuilding_id() { return building_id; }
    public int getSensor_id() { return sensor_id; }

}

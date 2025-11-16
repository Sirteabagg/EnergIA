package com.example.api.dto;

import com.example.api.dao.MeasureDAO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"name", "measures"})
public class SensorDTO {
    private String name;
    private List<MeasureDAO.Measure> sensorMeasure;

    public SensorDTO(String name, List<MeasureDAO.Measure> sensorMeasure) {
        this.name = name;
        this.sensorMeasure = sensorMeasure;
    }

    public String getName() { return name; }
    public List<MeasureDAO.Measure> getMeasures() { return this.sensorMeasure; }
}

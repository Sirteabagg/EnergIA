package com.example.api.dto;

import com.example.api.dao.MeasureDAO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"name", "measures"})
public class BuildingDTO {

    private String building_name;
    private List<MeasureDAO.Measure> buildingMeasures;
    // getters & setters

    public BuildingDTO(String building_name, List<MeasureDAO.Measure> buildingMeasures) {
        this.building_name = building_name;
        this.buildingMeasures = buildingMeasures;
    }

    // Getters
    public String getName() { return this.building_name; }
    public List<MeasureDAO.Measure> getMeasures() { return this.buildingMeasures; }

}

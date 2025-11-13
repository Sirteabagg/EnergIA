package com.example.api.dto;

public class SensorDTO {
    private int id;
    private String name;

    public SensorDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

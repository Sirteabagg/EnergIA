package com.example.api.dto;

public class BuildingDTO {

    private int id;
    private String name;

    public BuildingDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

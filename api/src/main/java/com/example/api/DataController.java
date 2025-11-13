package com.example.api;

import com.example.api.dto.SensorDTO;
import com.example.api.dto.BuildingDTO;

import com.example.api.dto.SummaryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/data")
public class DataController {
    @GetMapping("/summary")
    public String getSummary() { return "Bâtiments : 12, Capteurs : 67, Anomalies : 2"; }

//    @GetMapping("/buildings/{id}")
//    public BuildingDTO getBuilding(@PathVariable Long id) { /* retourne un bâtiment */ }
//
//    @GetMapping("/anomalies")
//    public List<SensorDTO> getAnomalies() { /* retourne les capteurs anormaux */ }
}
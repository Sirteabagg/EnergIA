package com.example.api;

import com.example.api.dao.BuildingDAO;
import com.example.api.dao.ConnectionDAO;
import com.example.api.dao.MeasureDAO;
import com.example.api.dao.SensorDAO;
import com.example.api.dto.SensorDTO;
import com.example.api.dto.BuildingDTO;

import com.example.api.dto.SummaryDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/data")
public class DataController {
    @GetMapping("/summary")
    public SummaryDTO getSummary(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        // Remplace par tes vrais identifiants
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        // On crée la connexion à la base EnergIA
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        MeasureDAO measureDAO = new MeasureDAO(connectionDAO);

        // Tu peux mettre à jour ici:
        int buildings = 0; // À remplacer plus tard par buildingDAO.countBuildings()
        int sensors = 0;
        int anomalies = 0;

        List<MeasureDAO.AverageConsumption> averages = null;
        try {
            averages = measureDAO.getAverageConsumptionByBuilding(startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            // Gère l'erreur, éventuellement renvoie une 500
            return null;
        }


        return new SummaryDTO(buildings, sensors, anomalies, averages);
    }



    @GetMapping("/buildings/{id}")
    public BuildingDTO getBuilding(@PathVariable int id) {
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        MeasureDAO measureDAO = new MeasureDAO(connectionDAO);
        BuildingDAO buildingDAO = new BuildingDAO(connectionDAO);

        String building_name = "";
        List<MeasureDAO.Measure> buildingMeasures = null;

        try {
            building_name = buildingDAO.getBuildingNameById(id);
            buildingMeasures = measureDAO.getMeasuresByBuilding(id);
        } catch (Exception e) {
            e.printStackTrace();
            // Gère l'erreur, éventuellement renvoie une 500
            return null;
        }

        return new BuildingDTO(building_name, buildingMeasures);
    }
//
    @GetMapping("/anomalies")
    public List<SensorDTO> getAnomalies() {
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        SensorDAO sensorDao = new SensorDAO(connectionDAO);

        List<Integer> sensors_ids = new ArrayList<>();
        List<SensorDTO> sensorsMeasures = new ArrayList<>();

        String sensor_name = null;
        List<MeasureDAO.Measure> sensor_measure = null;

        try {
            sensors_ids = sensorDao.getAllIds();
            for (Integer sensor_id : sensors_ids) {
                sensor_name = sensorDao.getSensorNameById(sensor_id);
                sensor_measure = MeasureDAO.getMeasuresBySensors(sensor_id);
                sensorsMeasures.add(new SensorDTO(sensor_name, sensor_measure));
            }

         } catch (Exception e) {
            e.printStackTrace();
            // Gère l'erreur, éventuellement renvoie une 500
            return null;
        }

        return sensorsMeasures;
    }
}
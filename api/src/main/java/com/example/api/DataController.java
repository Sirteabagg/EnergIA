package com.example.api;

import com.example.api.dao.*;
import com.example.api.dto.BuildingAverageDTO;
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
    public SummaryDTO getSummary() {
        // Remplace par tes vrais identifiants
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        // On crée la connexion à la base EnergIA
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        MeasureDAO measureDAO = new MeasureDAO(connectionDAO);
        BuildingDAO buildingDAO = new BuildingDAO(connectionDAO);
        SensorDAO sensorDAO = new SensorDAO(connectionDAO);
        ErrorDAO errorDAO = new ErrorDAO(connectionDAO);

        int buildings = 0;
        int sensors = 0;
        int anomalies = 0;
        float averageHumidityGlobal;
        float averageTemperatureGlobal;
        float averagePowerConsumptionGlobal;

        List<Float> listAverages;

        List<BuildingAverageDTO> buildingAverages = new ArrayList<>();

        List<Integer> buildingIds;

        String buildingName = "";
        List<Float> buildingAverage;
        try {
            buildings = buildingDAO.getNumberOfBuildings();
            sensors = sensorDAO.getNumberOfSensors();
            anomalies = errorDAO.getNumberAnomalies();
            listAverages = measureDAO.getAverageMeasures();
            averageHumidityGlobal = listAverages.get(0);
            averageTemperatureGlobal = listAverages.get(1);
            averagePowerConsumptionGlobal = listAverages.get(2);

            buildingIds = buildingDAO.getAllIds();

            for (Integer buildingId : buildingIds) {
                buildingName = buildingDAO.getBuildingNameById(buildingId);
                buildingAverage = measureDAO.getAverageMeasureByBuilding(buildingId);
                buildingAverages.add(new BuildingAverageDTO(buildingName, buildingAverage.get(0), buildingAverage.get(1), buildingAverage.get(2)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return new SummaryDTO(buildings, sensors, anomalies,
                averageHumidityGlobal, averageTemperatureGlobal, averagePowerConsumptionGlobal,
                buildingAverages);
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
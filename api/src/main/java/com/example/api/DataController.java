package com.example.api;

import com.example.api.dao.*;
import com.example.api.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
        MeasureDAO measureDAO = new MeasureDAO();
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

    @GetMapping("/data/building")
    public BuildingAvgPeriodDTO getAverageConsumptionByBuilding(
            @RequestParam("start") String startDate,
            @RequestParam("end") String endDate
    ) {
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        MeasureDAO measureDAO = new MeasureDAO();

        List<MeasureDAO.AverageConsumption> rawResults = new ArrayList<>();

        try {
            // Recupère la moyenne par batiment entre start/end en une seule requête
            rawResults = measureDAO.getAverageConsumptionByBuilding(startDate, endDate);

            return new BuildingAvgPeriodDTO(startDate, endDate, rawResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (BuildingAvgPeriodDTO) Collections.emptyList();
    }

    @GetMapping("/data/building/overconsumption")
    public List<String> getAverageConsumptionByBuilding() {
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        BuildingDAO buildingDAO = new BuildingDAO(connectionDAO);
        List<MeasureDAO.AverageConsumption> rawResults = new ArrayList<>();

        try {
            // Recupère la moyenne par batiment entre start/end en une seule requête
            return buildingDAO.getOverconsummingBuilding();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/buildings/{id}")
    public BuildingDTO getBuilding(@PathVariable int id) {
        String dbUser = "root";
        String dbPassword = "root";
        String dbName = "EnergIA";
        ConnectionDAO connectionDAO = ConnectionDAO.getInstance(dbName, dbUser, dbPassword);
        MeasureDAO measureDAO = new MeasureDAO();
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

    @RestController
    @RequestMapping("/api/v1/python-script")
    public class PythonScriptController {

        @GetMapping("/run")
        public ResponseEntity<String> runPythonScript() {
            try {
                String projectRoot = System.getProperty("user.dir"); // Racine d'exécution
                Path scriptPath = Paths.get(projectRoot, "../scripts/manage.py").normalize();
                ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath.toString());
                processBuilder.environment().put("PYTHONPATH", "../data/scripts");
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                // Capture output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    return ResponseEntity.ok(output.toString());
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Erreur lors de l'exécution du script Python:\n" + output);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }

}
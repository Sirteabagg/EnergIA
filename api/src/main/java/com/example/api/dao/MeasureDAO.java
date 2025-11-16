package com.example.api.dao;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeasureDAO {

    private static ConnectionDAO connectionDAO;

    public MeasureDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    public static List<AverageConsumption> getAverageConsumptionByBuilding(String startDate, String endDate) throws SQLException {
        List<AverageConsumption> result = new ArrayList<>();

        String sql = "{CALL get_average_consumption_by_building(?, ?)}";
        // Essaie d'utiliser la connexion via ton DAO
        try (Connection conn = connectionDAO.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, startDate); // format: "2025-01-01"
            stmt.setString(2, endDate);   // format: "2025-12-31"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int buildingId = rs.getInt("building_id");
                    double average = rs.getDouble("average_consumption");
                    result.add(new AverageConsumption(buildingId, average));
                }
            }
        }
        return result;
    }




    // À adapter selon ce que tu veux en sortie
    public static class AverageConsumption {
        public int buildingId;
        public double averageConsumption;

        public AverageConsumption(int buildingId, double averageConsumption) {
            this.buildingId = buildingId;
            this.averageConsumption = averageConsumption;
        }
    }

    public static List<Measure> getMeasuresByBuilding(int idBuilding) throws SQLException {
        List<Measure> result = new ArrayList<>();

        String sql = "{CALL get_building_info_by_id(?)}";
        // Essaie d'utiliser la connexion via ton DAO
        try (Connection conn = connectionDAO.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idBuilding); // format: "2025-01-01"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String timestamp = rs.getString("timestamp");
                    float temperature = rs.getFloat("temperature");
                    float humidity = rs.getFloat("humidity");
                    float powerConsumption = rs.getFloat("power_consumption");
                    result.add(new Measure(timestamp,temperature, humidity, powerConsumption));
                }
            }
        }
        return result;
    }

    public static List<Measure> getMeasuresBySensors(int idSensor) throws SQLException {
        List<Measure> result = new ArrayList<>();

        String sql = "SELECT timestamp, temperature, humidity, power_consumption\n" +
                "FROM Error\n" +
                "WHERE sensor_id = ?\n" +
                "ORDER BY timestamp ASC;";
        // Essaie d'utiliser la connexion via ton DAO
        try (Connection conn = connectionDAO.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idSensor); // format: "2025-01-01"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String timestamp = rs.getString("timestamp");
                    float temperature = rs.getFloat("temperature");
                    float humidity = rs.getFloat("humidity");
                    float powerConsumption = rs.getFloat("power_consumption");
                    result.add(new Measure(timestamp,temperature, humidity, powerConsumption));
                }
            }
        }
        return result;
    }

    public static class Measure {
        public String timestamp;
        public float temperature;
        public float humidity;
        public float powerConsumption;

        public Measure(String timestamp, float temperature, float humidity, float powerConsumption) {
            this.timestamp = timestamp;
            this.temperature = temperature;
            this.humidity = humidity;
            this.powerConsumption = powerConsumption;
        }
    }

}

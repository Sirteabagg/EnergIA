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

            stmt.setInt(1, idSensor);

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

    public List<Float> getAverageMeasures() throws SQLException {
        String sql = "SELECT AVG(humidity), AVG(temperature), AVG(power_consumption) FROM Measure;";
        try (Connection conn = connectionDAO.getConnection();
        CallableStatement stmt = conn.prepareCall(sql)) {
            List<Float> averages = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    averages.add(rs.getFloat("AVG(humidity)"));
                    averages.add(rs.getFloat("AVG(temperature)"));
                    averages.add(rs.getFloat("AVG(power_consumption)"));
                    return averages;
                }
                return null;
            }
        }
    }

    public List<Float> getAverageMeasureByBuilding(int idBuilding) throws SQLException {
        String sql = "SELECT AVG(humidity), AVG(temperature), AVG(power_consumption) FROM Measure" +
                " WHERE building_id = ?;";
        try (Connection conn = connectionDAO.getConnection();
        CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idBuilding);

            List<Float> averages = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    averages.add(rs.getFloat("AVG(humidity)"));
                    averages.add(rs.getFloat("AVG(temperature)"));
                    averages.add(rs.getFloat("AVG(power_consumption)"));
                    return averages;
                }
                return null;
            }
        }
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

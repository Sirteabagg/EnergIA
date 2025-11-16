package com.example.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SensorDAO {

    private static ConnectionDAO connectionDAO;

    public SensorDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    public String getSensorNameById(int id) throws SQLException {
        String sql = "SELECT name FROM Sensor WHERE sensor_id = ?";
        try (Connection conn = connectionDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    return null;
                }
            }
        }
    }

    public List<Integer> getAllIds() throws SQLException {
        String sql = "SELECT sensor_id FROM Sensor";
        try (Connection conn = connectionDAO.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> ids = new ArrayList<>();
                while (rs.next()) {
                    ids.add(rs.getInt("sensor_id"));
                }
                return ids;
            }
        }
    }

    public int getNumberOfSensors() throws SQLException {
        String sql = "SELECT COUNT(sensor_id) FROM Sensor";
        try (Connection conn = connectionDAO.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }

}

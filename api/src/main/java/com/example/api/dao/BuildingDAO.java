package com.example.api.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingDAO {

    private static ConnectionDAO connectionDAO;

    public BuildingDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    public String getBuildingNameById(int id) throws SQLException {
        String sql = "SELECT name FROM Building WHERE building_id = ?";
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

    public int getNumberOfBuildings() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Building";
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

    public List<Integer> getAllIds() throws SQLException {
        String sql = "SELECT building_id FROM Building";
        try (Connection conn = connectionDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Integer> ids = new ArrayList<>();
                while (rs.next()) {
                    ids.add(rs.getInt("building_id"));
                }
                return ids;
            }
        }
    }

}

package com.example.api.dao;

import java.sql.*;

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


}

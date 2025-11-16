package com.example.api.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ErrorDAO {

    private static ConnectionDAO connectionDAO;

    public ErrorDAO(ConnectionDAO connectionDAO) {
        this.connectionDAO = connectionDAO;
    }

    public int getNumberAnomalies() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Error;";
        try (Connection conn = connectionDAO.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}

package com.healthcare;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        try (Connection con = DBUtil.getConnection()) {
            System.out.println("✅ DB Connected: " + con);
        } catch (SQLException e) {
            System.out.println("❌ DB Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


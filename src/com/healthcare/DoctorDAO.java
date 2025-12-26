package com.healthcare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Doctor add karne ke liye
    public void addDoctor(Doctor d) throws SQLException {

        String sql = "INSERT INTO doctors(name, age, specialization) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getName());
            ps.setInt(2, d.getAge());
            ps.setString(3, d.getSpecialization());

            ps.executeUpdate();
        }
    }

    // Saare doctors laane ke liye
    public List<Doctor> getAllDoctors() {

        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT id, name, age, specialization FROM doctors";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                // 🔴 phone DB me nahi hai, isliye dummy value
                String phone = "N/A";

                Doctor d = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        phone,
                        rs.getString("specialization")
                );

                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}


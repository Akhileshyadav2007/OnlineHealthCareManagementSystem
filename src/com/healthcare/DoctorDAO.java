package com.healthcare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // Doctor ko DB me insert karega
    public void addDoctor(Doctor d) throws SQLException {
        String sql = "INSERT INTO doctors(name, age, specialization) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, d.getName());
            ps.setInt(2, d.getAge());
            ps.setString(3, d.getSpecialization());

            ps.executeUpdate();

            // Agar Doctor class me setId() hai to yahan set bhi kar sakte ho
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Doctor saved with ID: " + id);
                    // d.setId(id);
                }
            }
        }
    }

    // Saare doctors DB se laayega
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT id, name, age, specialization FROM doctors";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String specialization = rs.getString("specialization");

                // Tumhari Doctor class ka constructor:
                // new Doctor(int id, String name, int age, String phone, String specialization)
                String phone = "N/A"; // DB me phone column nahi hai, isliye dummy
                Doctor d = new Doctor(id, name, age, phone, specialization);

                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}


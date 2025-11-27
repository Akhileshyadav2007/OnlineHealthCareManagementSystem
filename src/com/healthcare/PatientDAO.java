package com.healthcare;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Patient ko database me INSERT karega
    public void savePatient(Patient p) throws SQLException {
        String sql = "INSERT INTO patients(name, age, disease) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getDisease());

            ps.executeUpdate();

            // Agar Patient class me setId() hai to use kar sakte hain,
            // lekin agar nahi hai to ignore kar dena safe hai.
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Patient saved with ID: " + id);
                    // Agar tumhari Patient class me setId(int id) ho to:
                    // p.setId(id);
                }
            }
        }
    }

    // Database se saare patients ko LIST me laayega
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();

        String sql = "SELECT id, name, age, disease FROM patients";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String disease = rs.getString("disease");

                // Tumhari Patient class jo constructor use kar rahi hai GUI me:
                // new Patient(0, name, age, phone, disease)
                // usi pattern ko yahan use karte hain:
                String phone = "N/A"; // kyunki DB me phone column nahi hai

                Patient p = new Patient(id, name, age, phone, disease);
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}


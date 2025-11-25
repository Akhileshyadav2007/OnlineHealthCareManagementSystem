package com.healthcare;

import java.sql.*;
import java.util.ArrayList;

public class PatientDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/healthcare_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password_here";

    public void savePatient(Patient p) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO patients(name, age, disease) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.name);
            ps.setInt(2, p.age);
            ps.setString(3, p.disease);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println("DB Error while saving patient: " + e.getMessage());
        }
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM patients";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("disease")
                );
                list.add(p);
            }
            con.close();

        } catch (Exception e) {
            System.out.println("DB Error while loading patients: " + e.getMessage());
        }

        return list;
    }
}


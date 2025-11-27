package com.healthcare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AppointmentDAO {

    // INSERT / book appointment
    public void addAppointment(Appointment a) {
        String sql =
                "INSERT INTO appointments(patient_id, doctor_id, appointment_date, description) " +
                "VALUES (?,?,?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getAppointmentDate()));
            ps.setString(4, a.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Error inserting appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // SELECT with JOIN -> patient name + doctor name bhi aayega
    public Map<Integer, Appointment> getAllAppointments() {
        Map<Integer, Appointment> map = new HashMap<>();

        String sql =
                "SELECT a.id, a.patient_id, a.doctor_id, a.appointment_date, a.description, " +
                "       p.name AS patient_name, d.name AS doctor_name " +
                "FROM appointments a " +
                "JOIN patients p ON a.patient_id = p.id " +
                "JOIN doctors d ON a.doctor_id = d.id";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setDoctorId(rs.getInt("doctor_id"));

                Timestamp ts = rs.getTimestamp("appointment_date");
                if (ts != null) {
                    LocalDateTime dt = ts.toLocalDateTime();
                    a.setAppointmentDate(dt);
                }

                a.setDescription(rs.getString("description"));
                a.setPatientName(rs.getString("patient_name"));
                a.setDoctorName(rs.getString("doctor_name"));

                map.put(a.getId(), a);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading appointments: " + e.getMessage());
            e.printStackTrace();
        }

        return map;
    }
}




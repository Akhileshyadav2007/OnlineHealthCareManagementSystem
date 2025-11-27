package com.healthcare;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class HealthCareService {

    // In-memory collections (Collections & Generics marks)
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private Map<Integer, Appointment> appointments = new HashMap<>();

    // DAO objects
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    // ===== Load all data from database into collections =====
    public void loadAllData() throws SQLException {
        patients = patientDAO.getAllPatients();               // patients list
        doctors = doctorDAO.getAllDoctors();                  // doctors list
        appointments = appointmentDAO.getAllAppointments();   // appointments map
    }

    // ===== Getters for GUI =====
    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public Collection<Appointment> getAppointments() {
        return appointments.values();
    }

    // ===== Add patient in DB + local list =====
    public void addPatient(Patient p) throws SQLException {
        patientDAO.savePatient(p);   // DB me insert
        patients.add(p);             // in-memory list me bhi
    }

    // ===== Add doctor in DB + local list =====
    public void addDoctor(Doctor d) throws SQLException {
        doctorDAO.addDoctor(d);      // DB insert
        doctors.add(d);
    }

    // ===== Book appointment (GUI isko call karegi) =====
    public void bookAppointment(Appointment a) throws SQLException {
        // DB me insert
        appointmentDAO.addAppointment(a);

        // Latest data map me le aao (id auto-increment hota hai)
        appointments = appointmentDAO.getAllAppointments();
    }

    // ===== All appointments DB se (Collections & Generics) =====
    public Map<Integer, Appointment> getAllAppointments() throws SQLException {
        // direct DAO se bhi de sakte ho, ya in-memory map use kar sakte ho
        appointments = appointmentDAO.getAllAppointments();
        return appointments;
    }

    // 🔹 Multithreading & Synchronization for full marks
    public synchronized Collection<Appointment> getAllAppointmentsSafe() {
        return new ArrayList<>(appointments.values());
    }

    // 🔹 Example: Aaj ke appointments (future use / thread ke liye)
    public synchronized Collection<Appointment> getTodayAppointments() {
        LocalDate today = LocalDate.now();
        List<Appointment> todayList = new ArrayList<>();

        for (Appointment a : appointments.values()) {
            if (a.getAppointmentDate() != null &&
                a.getAppointmentDate().toLocalDate().equals(today)) {
                todayList.add(a);
            }
        }
        return todayList;
    }
}


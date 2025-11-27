package com.healthcare;

import java.time.LocalDateTime;

public class Appointment {

    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentDate;
    private String description;

    // NEW: names for display in GUI
    private String patientName;
    private String doctorName;

    // ---- constructors ----
    public Appointment() {   // no-arg constructor (DAO, GUI ke liye)
    }

    public Appointment(int id, int patientId, int doctorId, LocalDateTime appointmentDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

    public Appointment(int id, int patientId, int doctorId,
                       LocalDateTime appointmentDate, String description) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    // Optional: full constructor with names (useful agar kahin zaroorat ho)
    public Appointment(int id, int patientId, int doctorId,
                       String patientName, String doctorName,
                       LocalDateTime appointmentDate, String description) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    // ---- getters & setters ----
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // NEW: names
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    @Override
    public String toString() {
        return "Appointment#" + id +
                " | PatientID: " + patientId +
                " | DoctorID: " + doctorId +
                " | Date: " + appointmentDate +
                " | Desc: " + description;
    }
}





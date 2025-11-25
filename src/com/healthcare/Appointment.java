package com.healthcare;

public class Appointment implements Bookable {
    Patient patient;
    Doctor doctor;

    public Appointment(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    @Override
    public void book() {
        System.out.println("\nAppointment successfully booked!");
    }

    public void display() {
        book();
        System.out.println("------ Appointment Details ------");
        patient.displayDetails();
        doctor.displayDetails();
    }
}


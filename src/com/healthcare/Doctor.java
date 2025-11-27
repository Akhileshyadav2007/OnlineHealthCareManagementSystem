package com.healthcare;

public class Doctor extends Person implements Bookable {

    private String specialization;

    public Doctor(int id, String name, int age, String phone, String specialization) {
        super(id, name, age, phone);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public boolean isAvailable() {
        return true; // For now doctor is always available
    }

    @Override
    public String getDetails() {
        return "Doctor -> ID: " + id +
                ", Name: " + name +
                ", Age: " + age +
                ", Phone: " + phone +
                ", Specialization: " + specialization;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}

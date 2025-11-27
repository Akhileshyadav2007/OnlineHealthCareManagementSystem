package com.healthcare;

public class Patient extends Person {

    private String disease;

    public Patient(int id, String name, int age, String phone, String disease) {
        super(id, name, age, phone);
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    @Override
    public String getDetails() {
        return "Patient -> ID: " + id +
                ", Name: " + name +
                ", Age: " + age +
                ", Phone: " + phone +
                ", Disease: " + disease;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}



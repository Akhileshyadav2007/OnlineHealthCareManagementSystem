package com.healthcare;

public class Doctor extends Person {

    String specialization;

    public Doctor(String name, String specialization) {
        super(name, 0); 
        this.specialization = specialization;
    }

    @Override
    public void displayDetails() {
        System.out.println("Doctor Name: " + name);
        System.out.println("Specialization: " + specialization);
    }
}



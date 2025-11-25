package com.healthcare;

public class Patient extends Person {
    String disease;

    public Patient(String name, int age, String disease) {
        super(name, age); 
        this.disease = disease;
    }

    @Override
    public void displayDetails() {
        System.out.println("Patient Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Disease: " + disease);
    }
}


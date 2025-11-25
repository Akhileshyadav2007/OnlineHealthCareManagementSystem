package com.healthcare;

import java.util.*;

public class Main {

    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();

    public static void main(String[] args) {

        // 🔹 Start Background Thread (Multithreading + Daemon thread)
        Thread notificationThread = new Thread(new NotificationService());
        notificationThread.setDaemon(true);
        notificationThread.start();

        Scanner sc = new Scanner(System.in);

        // 🔹 Predefined doctors
        doctors.add(new Doctor("Dr. Akhilesh Yadav", "Cardiologist"));
        doctors.add(new Doctor("Dr. Dolly Yadav", "Eye Specialist"));
        doctors.add(new Doctor("Dr. Aman Pal", "Dentist"));

        int choice;

        do {
            System.out.println("\n===== ONLINE HEALTHCARE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Patient");
            System.out.println("2. View Doctor List");
            System.out.println("3. Book Appointment");
            System.out.println("4. View All Patients");
            System.out.println("5. Search Patient by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addPatient(sc);
                    break;

                case 2:
                    showDoctors();
                    break;

                case 3:
                    bookAppointment(sc);
                    break;

                case 4:
                    showAllPatients();
                    break;

                case 5:
                    searchPatient(sc);
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("❌ Invalid option, please try again!");
            }

        } while (choice != 6);

        sc.close();
    }

    // ---------- FUNCTIONS ----------

    private static void addPatient(Scanner sc) {
        sc.nextLine(); // clear buffer

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter disease: ");
        String disease = sc.nextLine();

        patients.add(new Patient(name, age, disease));
        System.out.println("✔ Patient added successfully!");
    }

    private static void showDoctors() {
        System.out.println("\n--- Available Doctors ---");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println("Doctor #" + (i + 1));
            doctors.get(i).displayDetails();
            System.out.println("---------------------");
        }
    }

    private static void showAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("⚠ No patients found!");
            return;
        }

        System.out.println("\n--- Patient List ---");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println("Patient #" + (i + 1));
            patients.get(i).displayDetails();
            System.out.println("---------------------");
        }
    }

    private static void searchPatient(Scanner sc) {
        if (patients.isEmpty()) {
            System.out.println("⚠ No patients available to search!");
            return;
        }

        sc.nextLine();
        System.out.print("Enter patient name to search: ");
        String name = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Person p : patients) {
            if (p.name.toLowerCase().contains(name)) {
                p.displayDetails();
                System.out.println("---------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ No patient found with this name.");
        }
    }

    private static void bookAppointment(Scanner sc) {
        if (patients.isEmpty()) {
            System.out.println("\n⚠ No patients found! Please add a patient first.");
            return;
        }

        System.out.println("\nSelect Patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).name);
        }
        System.out.print("Enter choice: ");
        int pindex = sc.nextInt() - 1;

        try {
            if (pindex < 0 || pindex >= patients.size()) {
                throw new PatientNotFoundException("❌ Invalid patient selection!");
            }

            System.out.println("\nSelect Doctor:");
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println((i + 1) + ". " + doctors.get(i).name);
            }

            System.out.print("Enter choice: ");
            int dindex = sc.nextInt() - 1;

            Appointment a = new Appointment(patients.get(pindex), doctors.get(dindex));
            a.display();

        } catch (PatientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}




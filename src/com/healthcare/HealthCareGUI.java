package com.healthcare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthCareGUI extends JFrame {

    private JTextField nameField;
    private JTextField ageField;
    private JTextField diseaseField;
    private JTextArea outputArea;

    private PatientDAO patientDAO = new PatientDAO();

    // GUI में doctors की list
    private List<Doctor> doctors = Arrays.asList(
            new Doctor("Dr. Akhilesh Yadav", "Cardiologist"),
            new Doctor("Dr. Dolly Yadav", "Eye Specialist"),
            new Doctor("Dr. Aman Pal", "Dentist")
    );

    public HealthCareGUI() {
        setTitle("Online Healthcare Management System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container
        JTabbedPane tabs = new JTabbedPane();

        // ---- Tab 1: Add Patient ----
        JPanel addPatientPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Patient Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel diseaseLabel = new JLabel("Disease:");

        nameField = new JTextField(20);
        ageField = new JTextField(5);
        diseaseField = new JTextField(20);

        JButton saveButton = new JButton("Save Patient");

        gbc.gridx = 0; gbc.gridy = 0;
        addPatientPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        addPatientPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addPatientPanel.add(ageLabel, gbc);
        gbc.gridx = 1;
        addPatientPanel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        addPatientPanel.add(diseaseLabel, gbc);
        gbc.gridx = 1;
        addPatientPanel.add(diseaseField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        addPatientPanel.add(saveButton, gbc);

        // Button action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePatient();
            }
        });

        // ---- Tab 2: View Patients ----
        JPanel viewPatientsPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        JButton loadPatientsBtn = new JButton("Load Patients From Database");
        loadPatientsBtn.addActionListener(e -> loadPatientsFromDB());

        viewPatientsPanel.add(scroll, BorderLayout.CENTER);
        viewPatientsPanel.add(loadPatientsBtn, BorderLayout.SOUTH);

        // ---- Tab 3: View Doctors ----
        JPanel doctorPanel = new JPanel(new BorderLayout());
        JTextArea doctorArea = new JTextArea();
        doctorArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Available Doctors:\n\n");
        for (Doctor d : doctors) {
            sb.append("Name: ").append(d.name)
              .append(" | Specialization: ").append(d.specialization)
              .append("\n");
        }
        doctorArea.setText(sb.toString());

        doctorPanel.add(new JScrollPane(doctorArea), BorderLayout.CENTER);

        // Tabs add करना
        tabs.addTab("Add Patient", addPatientPanel);
        tabs.addTab("View Patients", viewPatientsPanel);
        tabs.addTab("View Doctors", doctorPanel);

        add(tabs);
    }

    private void savePatient() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String disease = diseaseField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Patient p = new Patient(name, age, disease);

            // Database में save
            patientDAO.savePatient(p);

            JOptionPane.showMessageDialog(this,
                    "Patient saved successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            nameField.setText("");
            ageField.setText("");
            diseaseField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a number!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPatientsFromDB() {
        ArrayList<Patient> list = patientDAO.getAllPatients();
        if (list.isEmpty()) {
            outputArea.setText("No patients found in database.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Patients from Database:\n\n");
        for (Patient p : list) {
            sb.append("Name: ").append(p.name)
              .append(", Age: ").append(p.age)
              .append(", Disease: ").append(p.disease)
              .append("\n");
        }
        outputArea.setText(sb.toString());
    }
}



package com.healthcare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

public class HealthCareGUI extends JFrame {

    // ===== Patient tab components =====
    private JTextField nameField;
    private JTextField ageField;
    private JTextField diseaseField;
    private JTextArea outputArea;

    // ===== Appointment tab components =====
    private JTextField apptPatientIdField;
    private JTextField apptDoctorIdField;
    private JTextField apptDateTimeField;   // YYYY-MM-DD HH:MM
    private JTextField apptDescField;
    private JTable apptTable;

    // Service layer
    private final HealthCareService service = new HealthCareService();

    public HealthCareGUI() {
        setTitle("Online Healthcare Management System");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== DB se initial data load (patients, doctors, appointments) =====
        try {
            service.loadAllData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading initial data: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // =================== Main container ===================
        JTabbedPane tabs = new JTabbedPane();

        // ------------ Tab 1: Add Patient ------------
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

        saveButton.addActionListener(e -> savePatient());

        // ------------ Tab 2: View Patients ------------
        JPanel viewPatientsPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        JButton loadPatientsBtn = new JButton("Load Patients From Database");
        loadPatientsBtn.addActionListener(e -> loadPatientsFromDB());

        viewPatientsPanel.add(scroll, BorderLayout.CENTER);
        viewPatientsPanel.add(loadPatientsBtn, BorderLayout.SOUTH);

        // ------------ Tab 3: View Doctors (NOW FROM DB) ------------
        JPanel doctorPanel = new JPanel(new BorderLayout());
        JTextArea doctorArea = new JTextArea();
        doctorArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Available Doctors:\n\n");

        java.util.List<Doctor> doctorList = service.getDoctors();
        if (doctorList.isEmpty()) {
            sb.append("No doctors found in database.");
        } else {
            for (Doctor d : doctorList) {
                sb.append("ID: ").append(d.getId())
                  .append(" | Name: ").append(d.getName())
                  .append(" | Age: ").append(d.getAge())
                  .append(" | Specialization: ").append(d.getSpecialization())
                  .append("\n");
            }
        }

        doctorArea.setText(sb.toString());
        doctorPanel.add(new JScrollPane(doctorArea), BorderLayout.CENTER);

        // ------------ Tab 4: Book Appointment ------------
        JPanel bookApptPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;

        JLabel lblPid = new JLabel("Patient ID:");
        JLabel lblDid = new JLabel("Doctor ID:");
        JLabel lblDateTime = new JLabel("Date-Time (YYYY-MM-DD HH:MM):");
        JLabel lblDesc = new JLabel("Description:");

        apptPatientIdField = new JTextField(10);
        apptDoctorIdField = new JTextField(10);
        apptDateTimeField = new JTextField(16);
        apptDescField = new JTextField(20);

        JButton btnBookAppt = new JButton("Book Appointment");

        gbc2.gridx = 0; gbc2.gridy = 0;
        bookApptPanel.add(lblPid, gbc2);
        gbc2.gridx = 1;
        bookApptPanel.add(apptPatientIdField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 1;
        bookApptPanel.add(lblDid, gbc2);
        gbc2.gridx = 1;
        bookApptPanel.add(apptDoctorIdField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 2;
        bookApptPanel.add(lblDateTime, gbc2);
        gbc2.gridx = 1;
        bookApptPanel.add(apptDateTimeField, gbc2);

        gbc2.gridx = 0; gbc2.gridy = 3;
        bookApptPanel.add(lblDesc, gbc2);
        gbc2.gridx = 1;
        bookApptPanel.add(apptDescField, gbc2);

        gbc2.gridx = 1; gbc2.gridy = 4;
        gbc2.anchor = GridBagConstraints.CENTER;
        bookApptPanel.add(btnBookAppt, gbc2);

        btnBookAppt.addActionListener(e -> bookAppointment());

        // ------------ Tab 5: View Appointments ------------
        JPanel viewApptPanel = new JPanel(new BorderLayout());
        apptTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Patient ID", "Patient Name", "Doctor ID", "Doctor Name", "Date-Time", "Description"}, 0
        ));
        JScrollPane apptScroll = new JScrollPane(apptTable);

        JButton btnLoadAppts = new JButton("Load Appointments From DB");
        btnLoadAppts.addActionListener(e -> loadAppointmentsFromDB());

        viewApptPanel.add(apptScroll, BorderLayout.CENTER);
        viewApptPanel.add(btnLoadAppts, BorderLayout.SOUTH);

        // ===== Tabs add karna =====
        tabs.addTab("Add Patient", addPatientPanel);
        tabs.addTab("View Patients", viewPatientsPanel);
        tabs.addTab("View Doctors", doctorPanel);
        tabs.addTab("Book Appointment", bookApptPanel);
        tabs.addTab("View Appointments", viewApptPanel);

        add(tabs);
    }

    // ================== Methods ==================

    private void savePatient() {
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String disease = diseaseField.getText().trim();

        if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            String phone = "N/A"; // phone optional

            Patient p = new Patient(0, name, age, phone, disease);
            service.addPatient(p);

            JOptionPane.showMessageDialog(this,
                    "Patient saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            nameField.setText("");
            ageField.setText("");
            diseaseField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a number!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving patient: " + ex.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPatientsFromDB() {
        try {
            service.loadAllData();
            java.util.List<Patient> list = service.getPatients();

            if (list.isEmpty()) {
                outputArea.setText("No patients found in database.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Patients from Database:\n\n");
            for (Patient p : list) {
                sb.append("ID: ").append(p.getId())
                  .append(", Name: ").append(p.getName())
                  .append(", Age: ").append(p.getAge())
                  .append(", Disease: ").append(p.getDisease())
                  .append("\n");
            }
            outputArea.setText(sb.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading patients: " + ex.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookAppointment() {
        String pidText = apptPatientIdField.getText().trim();
        String didText = apptDoctorIdField.getText().trim();
        String dtText = apptDateTimeField.getText().trim();
        String desc = apptDescField.getText().trim();

        if (pidText.isEmpty() || didText.isEmpty() || dtText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill Patient ID, Doctor ID and Date-Time.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int pid = Integer.parseInt(pidText);
            int did = Integer.parseInt(didText);

            // "2025-11-28 10:30" -> "2025-11-28T10:30"
            LocalDateTime dateTime =
                    LocalDateTime.parse(dtText.replace(" ", "T"));

            Appointment a = new Appointment();
            a.setPatientId(pid);
            a.setDoctorId(did);
            a.setAppointmentDate(dateTime);
            a.setDescription(desc);

            service.bookAppointment(a);

            JOptionPane.showMessageDialog(this,
                    "Appointment booked successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            apptPatientIdField.setText("");
            apptDoctorIdField.setText("");
            apptDateTimeField.setText("");
            apptDescField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Patient ID and Doctor ID must be numbers!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error booking appointment: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAppointmentsFromDB() {
        try {
            Map<Integer, Appointment> map = service.getAllAppointments();

            DefaultTableModel model = (DefaultTableModel) apptTable.getModel();
            model.setRowCount(0); // clear table

            if (map.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No appointments found in database.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Appointment a : map.values()) {
                model.addRow(new Object[]{
                        a.getId(),
                        a.getPatientId(),
                        a.getPatientName(),
                        a.getDoctorId(),
                        a.getDoctorName(),
                        a.getAppointmentDate(),
                        a.getDescription()
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading appointments: " + ex.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


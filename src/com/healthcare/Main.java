package com.healthcare;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        // HealthCareService banaya
        HealthCareService service = new HealthCareService();

        // Database se data load karna
        try {
            service.loadAllData();
            System.out.println("Data loaded successfully from database!");
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        // 🔹 Multithreading: Background notification thread start
        NotificationService notificationService = new NotificationService(service);
        Thread notificationThread = new Thread(notificationService);
        notificationThread.setDaemon(true);
        notificationThread.start();

        // 🔹 GUI start (Java Swing)
        javax.swing.SwingUtilities.invokeLater(() -> {
            new HealthCareGUI();
        });
    }
}

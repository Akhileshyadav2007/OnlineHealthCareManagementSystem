package com.healthcare;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class GUIMain {
    public static void main(String[] args) {

        // Service object
        HealthCareService service = new HealthCareService();

        // DB se data load
        try {
            service.loadAllData();
            System.out.println("Data loaded successfully!");
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        // 🔹 Multithreading: Notification check thread
        NotificationService notificationService = new NotificationService(service);
        Thread t = new Thread(notificationService);
        t.setDaemon(true);
        t.start();

        // 🔹 GUI start
        SwingUtilities.invokeLater(() -> {
            HealthCareGUI gui = new HealthCareGUI();  // tumhare purane constructor ke hisaab se
            gui.setVisible(true);
        });
    }
}

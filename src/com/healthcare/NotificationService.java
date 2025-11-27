package com.healthcare;

import java.util.Collection;

public class NotificationService implements Runnable {

    private final HealthCareService service;
    private volatile boolean running = true;

    public NotificationService(HealthCareService service) {
        this.service = service;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                checkTodayAppointments();
                // 1 minute ke baad phir check karega
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    private void checkTodayAppointments() {
        // Synchronized methods HealthCareService me already hai
        Collection<Appointment> todays = service.getTodayAppointments();

        if (!todays.isEmpty()) {
            System.out.println("Reminder: Aaj ke appointments: " + todays.size());
            // Yaha future me GUI popup bhi laga sakte ho (JOptionPane)
        }
    }
}

package com.healthcare;

public class NotificationService implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);  // हर 5 सेकंड में reminder

                System.out.println("🔔 Reminder: Check today's appointments!");

            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
        }
    }
}



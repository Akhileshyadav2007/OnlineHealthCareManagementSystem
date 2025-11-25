package com.healthcare;

import javax.swing.SwingUtilities;

public class GUIMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HealthCareGUI gui = new HealthCareGUI();
            gui.setVisible(true);
        });
    }
}

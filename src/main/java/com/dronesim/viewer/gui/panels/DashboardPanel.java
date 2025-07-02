package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;
import com.dronesim.viewer.gui.components.TopSpeedRankingPanel;

public class DashboardPanel extends JPanel {
    public DashboardPanel(int droneId) {
    setLayout(new BorderLayout(10, 10));

    // Oben: Restart Button
    JButton restartButton = new JButton("Drücke hier um die Login-Daten zu wechseln");
    restartButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String javaBin = System.getProperty("java.home") + "/bin/java";
                String classPath = System.getProperty("java.class.path");

                List<String> command = new ArrayList<>();
                command.add(javaBin);
                command.add("-cp");
                command.add(classPath);
                command.add("com.dronesim.Main");

                new ProcessBuilder(command).start();
            } catch (Exception ex) {
                ex.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, "Fehler beim Neustart: " + ex.getMessage());
            }
            System.exit(0);
        }
    });
    
    add(restartButton, BorderLayout.NORTH);
    // Mitte: PieChart + Top 5 Rangliste
    JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    statsPanel.add(new DroneStatusChartPanel(10,8,5));
    statsPanel.add(new TopSpeedRankingPanel());
    add(statsPanel, BorderLayout.CENTER);

    // Unten: Drohnenliste
    DashboardTable tablePanel = new DashboardTable();
    add(tablePanel, BorderLayout.SOUTH);

    }
}

package com.dronesim.viewer.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.dronesim.viewer.gui.components.CarriageTypeCounterPanel;
import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;
import com.dronesim.viewer.gui.components.TopSpeedRankingPanel;
import com.dronesim.viewer.gui.components.WeightCategory;

/**
 * A dashboard panel that displays key drone statistics and a data table.
 * Includes a restart button, status pie chart, top speed ranking,
 * carriage type counts, and weight categories.
 */

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        java.util.List<com.dronesim.model.Drone> drones = new java.util.ArrayList<>();
        try {
            com.dronesim.api.DataFetcher fetcher = new com.dronesim.api.DataFetcher();
            drones = fetcher.fetchAllDrones();
        } catch (Exception e) {
            e.printStackTrace();
        }

    // Top: Restart Button
    JButton restartButton = new JButton("Click here to change login credentials");
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
    
    gbc.gridy = 0;
    gbc.weighty = 0.0;
    add(restartButton, gbc);

    /// Middle: Pie chart, Top 5 ranking, Carriage type counter and weight category panels.
    JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    statsPanel.add(new DroneStatusChartPanel(10,8,5));
    statsPanel.add(new TopSpeedRankingPanel());
    statsPanel.add(new CarriageTypeCounterPanel(drones));      
    statsPanel.add(new WeightCategory(drones));                

    gbc.gridy = 1;
    gbc.weighty = 0.3; 
    add(statsPanel, gbc);

    // Below: Data table (bottom)
    DashboardTable tablePanel = new DashboardTable();          

    gbc.gridy = 2;
    gbc.weighty = 0.6; 
    add(tablePanel, gbc);

    }
}

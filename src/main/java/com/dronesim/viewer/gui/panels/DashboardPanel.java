package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;
import com.dronesim.viewer.gui.components.TopSpeedRankingPanel;

public class DashboardPanel extends JPanel {
    public DashboardPanel(int droneId) {
    setLayout(new BorderLayout(10, 10));

        // Mitte: PieChart + Top 5 Rangliste
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.add(new DroneStatusChartPanel());
        statsPanel.add(new TopSpeedRankingPanel());
        add(statsPanel, BorderLayout.CENTER);

        // Unten: Drohnenliste
        DashboardTable tablePanel = new DashboardTable();
        add(tablePanel, BorderLayout.SOUTH);


    }
}

package com.dronesim.viewer.gui.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dronesim.viewer.gui.dialogs.TokenLoginDialog;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;
import com.dronesim.viewer.gui.components.TopSpeedRankingPanel;

public class DashboardPanel extends JPanel {
    public DashboardPanel(int droneId) {
        setLayout(new BorderLayout(10, 10));

        // Oben: Suchleiste
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        JTextField searchField = new JTextField();
        searchField.setToolTipText("Search drones...");

        JButton changeTokenBtn = new JButton("Change Token");
        changeTokenBtn.addActionListener(e -> {
            TokenLoginDialog dialog = new TokenLoginDialog((JFrame) javax.swing.SwingUtilities.getWindowAncestor(this));
            String newToken = dialog.getToken();
            String newUrl = dialog.getUrl();

            if (newToken != null && !newToken.isEmpty() && newUrl != null && !newUrl.isEmpty()) {
                try {
                    com.dronesim.api.ApiConfig.overrideAndSave(newUrl, newToken);
                    JOptionPane.showMessageDialog(this, "Token & URL wurden aktualisiert.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fehler beim Speichern: " + ex.getMessage());
                }
            }
        });

        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        searchPanel.add(new JLabel("🔍"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(changeTokenBtn, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

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

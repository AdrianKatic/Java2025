package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainTabbedPanel extends JPanel {
    private final JTabbedPane tabs;
    private DynamicsPanel dynamicsPanel;
    
    public MainTabbedPanel() {
        setLayout(new BorderLayout());

        // 2) Tab‐Pane
        tabs = new JTabbedPane();
        tabs.addTab("Dashboard", new DashboardPanel());
        // Dynamics‐Tab initial
        dynamicsPanel = new DynamicsPanel();
        tabs.addTab("Dynamics", dynamicsPanel);
        add(tabs, BorderLayout.CENTER);
        tabs.addTab("Drone Catalog", new CatalogPanel());
    }


    /** Fabrikmethode für ein frisches DynamicsPanel mit neuem Provider */
    
}

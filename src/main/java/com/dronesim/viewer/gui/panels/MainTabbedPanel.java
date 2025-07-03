package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The main panel containing all application tabs: Dashboard, Dynamics, and Drone Catalog.
 * 
 */

public class MainTabbedPanel extends JPanel {
    private final JTabbedPane tabs;
    private DynamicsPanel dynamicsPanel;
    
    public MainTabbedPanel() {
        setLayout(new BorderLayout());

        tabs = new JTabbedPane();
        tabs.addTab("Dashboard", new DashboardPanel());
        dynamicsPanel = new DynamicsPanel();
        tabs.addTab("Dynamics", dynamicsPanel);
        add(tabs, BorderLayout.CENTER);
        tabs.addTab("Drone Catalog", new CatalogPanel());
    }
    
}

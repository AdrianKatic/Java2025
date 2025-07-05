package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Main tab view with dashboard, catalog, and dynamics tabs.
 */
public class MainTabbedPanel extends JPanel {

    private final JTabbedPane tabs;
    private final DynamicsPanel dynamicsPanel;

    public MainTabbedPanel() {
        setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        dynamicsPanel = new DynamicsPanel();
        initTabs();
        add(tabs, BorderLayout.CENTER);
    }

    private void initTabs() {
        tabs.addTab("Dashboard", new DashboardPanel());
        tabs.addTab("Dynamics", dynamicsPanel);
        tabs.addTab("Drone Catalog", new CatalogPanel());
    }

    public DynamicsPanel getDynamicsPanel() {
        return dynamicsPanel;
    }
}

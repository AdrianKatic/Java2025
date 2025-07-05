package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dronesim.controller.CatalogController;
import com.dronesim.viewer.gui.components.DroneTablePanel;

/**
 * Shows the drone catalog (table + pagination).
 */
public class CatalogPanel extends JPanel {

    public CatalogPanel() {
        setLayout(new BorderLayout());

        DroneTablePanel tablePanel = new DroneTablePanel();
        add(new JScrollPane(tablePanel.getTable()), BorderLayout.CENTER);

        new CatalogController(tablePanel).loadDroneTypes();
    }
}

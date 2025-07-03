package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A panel that displays a sortable table of drone details using a JTable.
 */

public class DroneTablePanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;

    public DroneTablePanel() {
        String[] columns = {
            "DroneType ID", "Manufacturer", "Type Name", "Weight",
            "Max Speed", "Battery Capacity", "Control Range", "Max Carriage"
        };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        setLayout(new java.awt.BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
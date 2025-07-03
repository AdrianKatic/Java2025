package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DashboardTable extends JPanel{

    private final DefaultTableModel model;
    private final JTable table;

    public DashboardTable() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("All Drones"));

        String[] columns = {"ID", "Serial number", "Carriage weight", "Carriage type"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    public DefaultTableModel getModel() {
        return model;
    }
}

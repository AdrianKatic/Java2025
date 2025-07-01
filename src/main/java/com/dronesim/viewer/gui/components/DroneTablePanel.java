// package com.dronesim.viewer.gui.components;

// import java.awt.*;

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;

// import com.dronesim.model.DroneType;
// import com.dronesim.viewer.gui.panels.CatalogPanel;

// public class DroneTablePanel extends JPanel {
//     public DroneTablePanel() {
//         setLayout(new BorderLayout());
//         setBorder(BorderFactory.createTitledBorder("All Drones"));

//         String[] columns = {"ID", "Manufacturer", "Type Name", "Weight", "Max Speed", "Battery Capacity", "Control Range", "Max Carriage"};
//         DefaultTableModel model = new DefaultTableModel(columns, 0);
//         JTable table = new JTable(model);
//         table.setAutoCreateRowSorter(true);
//         add(new JScrollPane(table), BorderLayout.CENTER);
//         try {
//             for (DroneType dt : get()) {
//                 model.addRow(new Object[]{
//                     dt.getId(),
//                     dt.getManufacturer(),
//                     dt.getTypename(),
//                     dt.getWeight(),
//                     dt.getMax_speed(),
//                     dt.getBattery_capacity(),
//                     dt.getControl_range(),
//                     dt.getMax_carriage()
//                 });
//             }
//             } catch (Exception e) {
//                 JOptionPane.showMessageDialog(CatalogPanel.this,
//                     "Fehler beim Laden der DroneTypes:\n" + e.getMessage(),
//                     "Ladefehler", JOptionPane.ERROR_MESSAGE);
//             }


//         add(new JScrollPane(table), BorderLayout.CENTER);
//     }
// }
package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DroneTablePanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;

    public DroneTablePanel() {
        String[] columns = {
            "ID", "Manufacturer", "Type Name", "Weight",
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
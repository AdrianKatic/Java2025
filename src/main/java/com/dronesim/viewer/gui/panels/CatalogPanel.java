// package com.dronesim.viewer.gui.panels;

// import com.dronesim.api.DataFetcher;
// import com.dronesim.model.DroneType;

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.util.List;

// /**
//  * Ein Panel, das alle Attribute von DroneType in einer Tabelle anzeigt.
//  */
// public class CatalogPanel extends JPanel {
//     public CatalogPanel() {
//         setLayout(new BorderLayout());

//         // Spaltennamen entsprechend DroneType
//         String[] columns = {"ID", "Manufacturer", "Type Name", "Weight", "Max Speed", "Battery Capacity", "Control Range", "Max Carriage"};
//         DefaultTableModel model = new DefaultTableModel(columns, 0);
//         JTable table = new JTable(model);
//         table.setAutoCreateRowSorter(true);
//         add(new JScrollPane(table), BorderLayout.CENTER);

//         // Lade DroneTypes asynchron
//         new SwingWorker<List<DroneType>, Void>() {
//             @Override
//             protected List<DroneType> doInBackground() throws Exception {
//                 return new DataFetcher().fetchAllDroneTypes();
//             }
//             @Override
//             protected void done() {
//                 try {
//                     for (DroneType dt : get()) {
//                         model.addRow(new Object[]{
//                             dt.getId(),
//                             dt.getManufacturer(),
//                             dt.getTypename(),
//                             dt.getWeight(),
//                             dt.getMax_speed(),
//                             dt.getBattery_capacity(),
//                             dt.getControl_range(),
//                             dt.getMax_carriage()
//                         });
//                     }
//                 } catch (Exception e) {
//                     JOptionPane.showMessageDialog(CatalogPanel.this,
//                         "Fehler beim Laden der DroneTypes:\n" + e.getMessage(),
//                         "Ladefehler", JOptionPane.ERROR_MESSAGE);
//                 }
//             }
//         }.execute();
//     }
// }
package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dronesim.controller.CatalogController;
import com.dronesim.viewer.gui.components.DroneTablePanel;

/**
 * Panel für den Drone-Katalog, nutzt DroneTablePanel für die Anzeige.
 */
public class CatalogPanel extends JPanel {
    public CatalogPanel() {
        setLayout(new BorderLayout());

        DroneTablePanel tablePanel = new DroneTablePanel();
        add(new JScrollPane(tablePanel.getTable()), BorderLayout.CENTER);

        new CatalogController(tablePanel).loadDroneTypes();
    }
}
package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DashboardTable extends JPanel{
    // private DefaultTableModel model;
    // private JTable table;

    // public DashboardTable() {
    //     setLayout(new BorderLayout());
    //     setBorder(BorderFactory.createTitledBorder("All Drones"));

    //     String[] columns = {"ID", "Serial number", "Carriage weight", "Carriage type"};
    //     model = new DefaultTableModel(columns, 0);
    //     table = new JTable(model);
    //     add(new JScrollPane(table), BorderLayout.CENTER);

    //     // Lade Drone-Daten asynchron
    //     new SwingWorker<List<Drone>, Void>() {
    //         @Override
    //         protected List<Drone> doInBackground() throws Exception {
    //             return new DataFetcher().fetchAllDrones();
    //         }

    //         @Override
    //         protected void done() {
    //             try {
    //                 for (Drone d : get()) {
    //                     model.addRow(new Object[]{
    //                         d.getId(),
    //                         d.getSerialNumber(),
    //                         d.getCarriage_weight(),
    //                         d.getCarriage_type()
    //                     });
    //                 }
    //             } catch (Exception e) { 
    //                 JOptionPane.showMessageDialog(DashboardTable.this, "Fehler beim Laden der Drohnen:\n" + e.getMessage(),"Ladefehler", JOptionPane.ERROR_MESSAGE);
    //             }
    //         }
    //     }.execute();
    // }

    private final DefaultTableModel model;
    private final JTable table;

    public DashboardTable() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("All Drones"));

        String[] columns = {"ID", "Serial number", "Carriage weight", "Carriage type"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // public void updateTable(List<Drone> drones) {
    //     model.setRowCount(0); // alte Einträge löschen
    //     for (Drone d : drones) {
    //         model.addRow(new Object[]{
    //                 d.getId(),
    //                 d.getSerialNumber(),
    //                 d.getCarriage_weight(),
    //                 d.getCarriage_type()
    //         });
    //     }
    // }

    public DefaultTableModel getModel() {
        return model;
    }
}

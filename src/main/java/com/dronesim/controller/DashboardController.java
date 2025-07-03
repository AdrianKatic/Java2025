package com.dronesim.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.Drone;
import com.dronesim.viewer.gui.components.DashboardTable;

public class DashboardController {
   private final DashboardTable view;

    public DashboardController(DashboardTable view) {
        this.view = view;
    }

    /**
     * Loads drone data in the background with Swingworker and updates the table.
     * Shows an error dialog if loading fails.
     */
    public void loadDroneData() {
        new SwingWorker<List<Drone>, Void>() {
            @Override
            protected List<Drone> doInBackground() throws Exception {
                return new DataFetcher().fetchAllDrones();
            }

            @Override
            protected void done() {
                try {
                    List<Drone> drones = get();
                    DefaultTableModel model = view.getModel();
                    model.setRowCount(0); // deletes alle entries
                    for (Drone d : drones) {
                        model.addRow(new Object[]{
                                d.getId(),
                                d.getSerialNumber(),
                                d.getCarriageWeight(),
                                d.getCarriageType()
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view,
                            "Fehler beim Laden der Drohnen:\n" + e.getMessage(),
                            "Ladefehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    } 
}

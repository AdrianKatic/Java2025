package com.dronesim.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.DroneType;
import com.dronesim.viewer.gui.components.DroneTablePanel;

public class CatalogController {
    private final DroneTablePanel tablePanel;

    public CatalogController(DroneTablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public void loadDroneTypes() {
        new SwingWorker<List<DroneType>, Void>() {
            @Override
            protected List<DroneType> doInBackground() throws Exception {
                return new DataFetcher().fetchAllDroneTypes();
            }

            @Override
            protected void done() {
                try {
                    List<DroneType> types = get();
                    DefaultTableModel model = tablePanel.getModel();
                    for (DroneType dt : types) {
                        model.addRow(new Object[]{
                            dt.getId(),
                            dt.getManufacturer(),
                            dt.getTypename(),
                            dt.getWeight(),
                            dt.getMax_speed(),
                            dt.getBattery_capacity(),
                            dt.getControl_range(),
                            dt.getMax_carriage()
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Fehler beim Laden der DroneTypes:\n" + e.getMessage(),
                        "Ladefehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}

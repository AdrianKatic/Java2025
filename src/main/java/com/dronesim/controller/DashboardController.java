package com.dronesim.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.Drone;
import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;

public class DashboardController {
   private final DashboardTable view;
   private final DroneStatusChartPanel statusView;

    public DashboardController(DashboardTable view, DroneStatusChartPanel statusView) {
        this.view = view;
        this.statusView = statusView;
    }

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
                    model.setRowCount(0);
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

    public void loadStatusCounts() {
        new SwingWorker<int[],Void>() {
            @Override
            protected int[] doInBackground() throws Exception {
                return new DataFetcher().fetchAllDroneStatusCounts();
            }
            @Override
            protected void done() {
                try {
                    int[] c = get();
                    statusView.updateCounts(c[0], c[1], c[2]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    
}

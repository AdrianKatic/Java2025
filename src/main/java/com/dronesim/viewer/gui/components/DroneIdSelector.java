package com.dronesim.viewer.gui.components;

import java.awt.FlowLayout;
import java.util.List;
import java.util.function.IntConsumer;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.Drone;

public class DroneIdSelector extends JPanel {
    private final JComboBox<String> combo = new JComboBox<>();
    private final DataFetcher fetcher = new DataFetcher();
    private java.util.Map<String,Integer> serialToId = new java.util.HashMap<>();

    public DroneIdSelector(IntConsumer onLoad) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(new JLabel("Drone ID:"));
        add(combo);

        new javax.swing.SwingWorker<List<Drone>, Void>() {
            @Override
            protected List<Drone> doInBackground() throws Exception {
                return fetcher.fetchAllDrones();
            }
            @Override
            protected void done() {
                try {
                    List<Drone> all = get();
                    serialToId.clear();
                    combo.removeAllItems();
                    for (Drone d : all) {
                        String label = d.getId() + " - " + d.getSerialNumber();
                        serialToId.put(label, d.getId());
                        combo.addItem(label);
                    }
                    if (combo.getItemCount() == 0) {
                        JOptionPane.showMessageDialog(DroneIdSelector.this,
                            "No Drone with DronetypeID in Database",
                            "No Drones", JOptionPane.WARNING_MESSAGE);
                        combo.setEnabled(false);
                    } else {
                        combo.setEnabled(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DroneIdSelector.this,
                        "API-Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();

        combo.addActionListener(e -> {
            String label = (String) combo.getSelectedItem();
            if (label != null && serialToId.containsKey(label)) {
                int droneId = serialToId.get(label);
                onLoad.accept(droneId);
            }
        });
    }
}
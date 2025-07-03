package com.dronesim.viewer.gui.components;

import java.awt.FlowLayout;
import java.util.List;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.Drone;

public class DroneIdSelector extends JPanel {
    private final JTextField typeField = new JTextField("21", 5);
    private final JButton loadBtn   = new JButton("Load Serials");
    private final JComboBox<String> combo = new JComboBox<>();
    private final DataFetcher fetcher = new DataFetcher();
    // map serial→droneId:
    private java.util.Map<String,Integer> serialToId = new java.util.HashMap<>();

    public DroneIdSelector(IntConsumer onLoad) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(new JLabel("Drone-Type ID:"));
        add(typeField);
        add(loadBtn);
        add(combo);
        combo.setVisible(false);

        loadBtn.addActionListener(e -> {
            try {
                int typeId = Integer.parseInt(typeField.getText().trim());
                // 1) fetch all drones once
                List<Drone> all = fetcher.fetchAllDrones();
                serialToId.clear();
                combo.removeAllItems();
                // 2) filter by matching type
                for (Drone d : all) {
                    String typeUrl = d.getDroneType();
                    int parsed = Integer.parseInt(typeUrl.replaceAll(".*/(\\d+)/?$","$1"));
                    if (parsed == typeId) {
                        serialToId.put(d.getSerialNumber(), d.getId());
                        combo.addItem(d.getSerialNumber());
                    }
                }
                if (combo.getItemCount()==0) {
                    JOptionPane.showMessageDialog(this,
                        "Keine Seriennummern gefunden für typeId "+typeId,
                        "Keine Drohnen", JOptionPane.WARNING_MESSAGE);
                    combo.setVisible(false);
                } else {
                    combo.setVisible(true);
                    loadBtn.setText("Select Serial");
                }
                revalidate();
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Ungültige Type-ID", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "API-Fehler: "+ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        combo.addActionListener(e -> {
            String serial = (String)combo.getSelectedItem();
            if (serial!=null && serialToId.containsKey(serial)) {
                int droneId = serialToId.get(serial);
                onLoad.accept(droneId);
            }
        });
    }
}
package com.dronesim.viewer.gui.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dronesim.model.Drone;

public class CarriageTypeCounterPanel extends JPanel {

    public CarriageTypeCounterPanel(List<Drone> drones) {
        setBorder(BorderFactory.createTitledBorder("Carriage Type Counts"));
        setLayout(new java.awt.GridLayout(0, 1));
        Map<String, Integer> counts = new HashMap<>();

        for (Drone d : drones) {
            String type = d.getCarriage_type();
            if (type != null && !type.isEmpty()) {
                counts.put(type, counts.getOrDefault(type, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String typeCode = entry.getKey();
            String fullName;
            switch (typeCode) {
                case "NOT":
                    fullName = "No Payload";
                    break;
                case "SEN":
                    fullName = "Sensor Payload";
                    break;
                case "ACT":
                    fullName = "Actuator Payload";
                    break;
                default:
                    fullName = "Unknown";
            }
            JLabel label = new JLabel(typeCode + " (" + fullName + "): " + entry.getValue());
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setVerticalAlignment(JLabel.TOP);
            label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            add(label);
        }
    }
}

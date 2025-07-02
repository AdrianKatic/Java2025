package com.dronesim.viewer.gui.components;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dronesim.model.Drone;

public class WeightCategory extends JPanel {

    public WeightCategory(List<Drone> drones) {
        setLayout(new java.awt.GridLayout(0, 1, 0, 5));
        setPreferredSize(new Dimension(250, 120));
        setBorder(BorderFactory.createTitledBorder("Weight classes"));

        int leicht = 0;
        int mittel = 0;
        int schwer = 0;

        for (Drone d : drones) {
            double gewicht = d.getCarriage_weight();

            if (gewicht < 5.0) leicht++;
            else if (gewicht <= 10.0) mittel++;
            else schwer++;
        }

        add(new JLabel("Lightweight (< 5 kg): " + leicht));
        add(new JLabel("Middleweight (5–10 kg): " + mittel));
        add(new JLabel("Heavyweight (> 10 kg): " + schwer));
    }
}

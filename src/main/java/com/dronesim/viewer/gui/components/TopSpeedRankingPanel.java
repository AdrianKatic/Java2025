package com.dronesim.viewer.gui.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dronesim.model.DroneType;

public class TopSpeedRankingPanel extends JPanel {
    private final JLabel[] labels;

    public TopSpeedRankingPanel() {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createTitledBorder("Top 5 Fastest Drones"));
        setLayout(new GridLayout(5, 1));

        labels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            labels[i] = new JLabel((i + 1) + ". ---");
            add(labels[i]);
        }
    }

    public void updateRanking(List<DroneType> drones) {
        List<DroneType> top5 = drones.stream()
                .sorted(Comparator.comparingInt(DroneType::getMax_speed).reversed())
                .limit(5)
                .collect(Collectors.toList());

        for (int i = 0; i < labels.length; i++) {
            if (i < top5.size()) {
                DroneType d = top5.get(i);
                labels[i].setText((i + 1) + ". " + d.getTypename() + " - " + d.getMax_speed() + " km/h");
            } else {
                labels[i].setText((i + 1) + ". ---");
            }
        }
    }
}
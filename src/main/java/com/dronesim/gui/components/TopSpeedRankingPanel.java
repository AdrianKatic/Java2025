package com.dronesim.gui.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dronesim.model.DroneType;

public class TopSpeedRankingPanel extends JPanel {
    public TopSpeedRankingPanel() {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createTitledBorder("Top 5 Fastest Drones"));
        setLayout(new GridLayout(5, 1));

        new javax.swing.SwingWorker<List<DroneType>, Void>() {
            @Override
            protected List<DroneType> doInBackground() throws Exception {
                try {
                    return new com.dronesim.api.DataFetcher().fetchAllDroneTypes();
                } catch (Exception e) {
                    e.printStackTrace(); // ← Das druckt den Fehler in die Konsole
                    throw e; // ← So landet der Fehler trotzdem im catch-Block von done()
                }
            }

            // Ausgabe aller Drohnen im Terminal
            @Override
            protected void done() {
                try {
                    List<DroneType> droneTypeList = get();

                    System.out.println("Übersicht empfangen: " + droneTypeList.size());
                    droneTypeList.forEach(o -> {
                        System.out.println("Name: " + (o != null ? o.getTypename() : "null") + 
                                           ", Max Speed: " + (o != null ? o.getMax_speed() : "null"));
                    });

                    removeAll();
                    droneTypeList.stream()
                        .filter(o -> o != null && o.getMax_speed() > 0)
                        .sorted((a, b) -> Double.compare(b.getMax_speed(), a.getMax_speed()))
                        .limit(5)
                        .forEachOrdered(o -> {
                            String name = o.getTypename();
                            String speed = o.getMax_speed() + " km/h";
                            add(new JLabel(name + " - " + speed));
                        });
                    revalidate();
                    repaint();
                } catch (Exception e) {
                    add(new JLabel("Fehler beim Laden der Daten"));
                }
            }
        }.execute();
    }

    public TopSpeedRankingPanel(List<DroneOverview> overviewList) {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createTitledBorder("Top 5 Fastest Drones"));
        setLayout(new GridLayout(5, 1));

        overviewList.stream()
            .filter(o -> o.getDynamics() != null && o.getDynamics().getSpeed() > 0)
            .sorted((a, b) -> Double.compare(b.getDynamics().getSpeed(), a.getDynamics().getSpeed()))
            .limit(5)
            .forEachOrdered(o -> {
                String name = o.getType().getTypename();
                String speed = o.getDynamics().getSpeed() + " km/h";
                add(new JLabel(name + " - " + speed));
            });
    }
}
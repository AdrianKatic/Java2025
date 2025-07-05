package com.dronesim.viewer.gui.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dronesim.model.DroneOverview;
import com.dronesim.model.DroneType;

/**
 * Shows top 5 drones sorted by speed in a chart.
 */
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
                    e.printStackTrace();
                    throw e;
                }
            }

            @Override
            protected void done() {
                try {
                    List<DroneType> droneTypeList = get();

                    removeAll();
                    int[] rank = {1};
                    droneTypeList.stream()
                            .filter(o -> o != null && o.getMaxSpeed() > 0)
                            .sorted((a, b) -> Double.compare(b.getMaxSpeed(), a.getMaxSpeed()))
                            .limit(5)
                            .forEachOrdered(o -> {
                                String place = switch (rank[0]) {
                                    case 1 ->
                                        "1st Place";
                                    case 2 ->
                                        "2nd Place";
                                    case 3 ->
                                        "3rd Place";
                                    default ->
                                        rank[0] + "th Place";
                                };
                                String name = o.getTypename();
                                String speed = o.getMaxSpeed() + " km/h";
                                add(new JLabel(place + ": " + name + " - " + speed));
                                rank[0]++;
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
                    add(new JLabel(o + " " + name + " - " + speed));
                });
    }
}

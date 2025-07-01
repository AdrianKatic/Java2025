package com.dronesim.viewer.gui.pichart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.JPanel;

public class AllDronesStatusPieChartPanel extends JPanel{
   /**
 * The AllDronesStatusPieChartPanel class is a JPanel that displays a pie chart of all drones' statuses.
 */

    /**
     * Constructor for AllDronesStatusPieChartPanel.
     *
     * @param droneStatusService the drone status service
     */
    public AllDronesStatusPieChartPanel(DroneStatusService droneStatusService) {
        super(new BorderLayout()); // Set a layout manager

        PieChartPanel.Slice slice1 = new PieChartPanel.Slice(
                droneStatusService.getOnlineCount(), Color.GREEN, "Online");
        PieChartPanel.Slice slice2 = new PieChartPanel.Slice(
                droneStatusService.getIssueCount(), Color.ORANGE, "Issue");
        PieChartPanel.Slice slice3 = new PieChartPanel.Slice(
                droneStatusService.getOfflineCount(), Color.RED, "Offline");

        PieChartPanel pieChartPanel = new PieChartPanel(Arrays.asList(slice1, slice2, slice3));

        add(pieChartPanel, BorderLayout.CENTER); // Add to the center of the card
    }
} 


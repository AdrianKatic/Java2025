package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;

/**
 * A panel that displays a pie chart of drone status counts (Online, Offline, Issue).
 */

public class DroneStatusChartPanel extends JPanel {
    private final DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    private final PiePlot plot;
    private final ChartPanel chartPanel;

    public DroneStatusChartPanel() {
        this(0, 0, 0);
    }

    public DroneStatusChartPanel(int onlineDroneStatusCount, int offlineDroneStatusCount, int issueDroneStatusCount) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Drone Status Distribution"));

        if (onlineDroneStatusCount > 0) dataset.setValue("Online (" + onlineDroneStatusCount + ")", onlineDroneStatusCount);
        if (offlineDroneStatusCount > 0) dataset.setValue("Offline (" + offlineDroneStatusCount + ")", offlineDroneStatusCount);
        if (issueDroneStatusCount > 0) dataset.setValue("Issue (" + issueDroneStatusCount + ")", issueDroneStatusCount);

        JFreeChart pieChart = ChartFactory.createPieChart(
            null,
            dataset,
            true,
            true,
            false
        );

        this.plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Online (" + onlineDroneStatusCount + ")", Color.GREEN);
        plot.setSectionPaint("Offline (" + offlineDroneStatusCount + ")", Color.RED);
        plot.setSectionPaint("Issue (" + issueDroneStatusCount + ")", Color.YELLOW);
        
        plot.setLabelGenerator(null);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setInsets(new RectangleInsets(10, 10, 10, 10));
        plot.setInteriorGap(0.04);
        plot.setSimpleLabels(true);

        chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(300, 300));
        add(chartPanel, BorderLayout.CENTER);

    }

    public void updateCounts(int online, int offline, int issue) {
        dataset.clear();
        if (online  > 0) dataset.setValue("Online ("  + online  + ")", online);
        if (offline > 0) dataset.setValue("Offline (" + offline + ")", offline);
        if (issue   > 0) dataset.setValue("Issue ("   + issue   + ")", issue);

        plot.setSectionPaint("Online ("  + online  + ")", Color.GREEN);
        plot.setSectionPaint("Offline (" + offline + ")", Color.RED);
        plot.setSectionPaint("Issue ("   + issue   + ")", Color.YELLOW);

        chartPanel.getChart().fireChartChanged();
    }

}

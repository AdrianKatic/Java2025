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

public class DroneStatusChartPanel extends JPanel {
    private ChartPanel chartPanel;

    public DroneStatusChartPanel(int onlineDroneStatusCount, int offlineDroneStatusCount, int issueDroneStatusCount) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Drone Status Distribution"));

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        if (onlineDroneStatusCount > 0) dataset.setValue("Online (" + onlineDroneStatusCount + ")", onlineDroneStatusCount);
        if (offlineDroneStatusCount > 0) dataset.setValue("Offline (" + offlineDroneStatusCount + ")", offlineDroneStatusCount);
        if (issueDroneStatusCount > 0) dataset.setValue("Issue (" + issueDroneStatusCount + ")", issueDroneStatusCount);

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Status Overview",
            dataset,
            true,
            true,
            false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
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


}

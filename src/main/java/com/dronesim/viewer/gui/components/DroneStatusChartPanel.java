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
    public DroneStatusChartPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Drone Status Distribution"));

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Online", 10);
        dataset.setValue("Offline", 5);
        dataset.setValue("Issue", 3);

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Status Overview",
            dataset,
            true,
            true,
            false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Online", Color.GREEN);
        plot.setSectionPaint("Offline", Color.RED);
        plot.setSectionPaint("Issue", Color.YELLOW);
        plot.setLabelGenerator(null);


        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setInsets(new RectangleInsets(10, 10, 10, 10));
        plot.setInteriorGap(0.04);
        plot.setSimpleLabels(true);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(300, 300));
        chartPanel.setMaximumSize(new Dimension(300, 300));
        chartPanel.setMinimumSize(new Dimension(300, 300));
        add(chartPanel, BorderLayout.CENTER);
    }
}

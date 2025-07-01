package com.dronesim.viewer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.general.DefaultPieDataset;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.DroneDynamics;

public class DroneStatusChartPanel extends JPanel {
    private ChartPanel chartPanel;

    public DroneStatusChartPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Drone Status Distribution"));

        chartPanel = new ChartPanel(null);
        chartPanel.setPreferredSize(new Dimension(300, 300));
        add(chartPanel, BorderLayout.CENTER);

        loadDataAsync();
    }

    private void loadDataAsync() {
        new SwingWorker<List<DroneDynamics>, Void>() {
            @Override
            protected List<DroneDynamics> doInBackground() throws Exception {
                return new DataFetcher().fetchAllDroneDynamics(0, 200);
            }

            @Override
            protected void done() {
                try {
                    List<DroneDynamics> data = get();
                    updateChart(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void updateChart(List<DroneDynamics> dynamicsList) {
        int online = 0, offline = 0, issue = 0;

        for (DroneDynamics d : dynamicsList) {
            String status = d.getStatus();
            if ("ON".equalsIgnoreCase(status) || "Online".equalsIgnoreCase(status)) {
                online++;
            } else if ("OFF".equalsIgnoreCase(status) || "Offline".equalsIgnoreCase(status)) {
                offline++;
            } else {
                issue++;
            }
        }

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        if (online > 0) dataset.setValue("Online", online);
        if (offline > 0) dataset.setValue("Offline", offline);
        if (issue > 0) dataset.setValue("Issue", issue);

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

        chartPanel.setChart(pieChart); 
        revalidate();
        repaint();
    }
}

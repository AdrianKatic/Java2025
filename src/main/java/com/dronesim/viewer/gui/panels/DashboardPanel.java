package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.json.JSONObject;

import com.dronesim.api.DataFetcher;
import com.dronesim.model.DroneDynamics;
import com.dronesim.parser.ManualJsonParser;
import com.dronesim.viewer.gui.components.DashboardTable;
import com.dronesim.viewer.gui.components.DroneStatusChartPanel;
import com.dronesim.viewer.gui.components.TopSpeedRankingPanel;
import com.dronesim.viewer.gui.pichart.DroneStatusService;
import com.dronesim.viewer.gui.pichart.PieChartPanel;

public class DashboardPanel extends JPanel {
    public DashboardPanel(int droneId) {
    setLayout(new BorderLayout(10, 10));


        // Mitte: PieChart + Top 5 Rangliste
        DroneStatusService droneStatusService = new DroneStatusService(fetchAllDynamics());


        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        statsPanel.add(new PieChartPanel(droneStatusService));
        statsPanel.add(new TopSpeedRankingPanel());
        add(statsPanel, BorderLayout.CENTER);



        // Unten: Drohnenliste
        DashboardTable tablePanel = new DashboardTable();
        add(tablePanel, BorderLayout.SOUTH);


    }

    public List<DroneDynamics> fetchAllDynamics() throws Exception {
        // 1) Erstes Request, um Gesamtzahl abzurufen
        static String firstJson = com.dronesim.api.ApiClient.getJson("/api/dronedynamics/?limit=1&offset=0");
        JSONObject root = new JSONObject(firstJson);
        int total = root.getInt("count");

        // 2) Dann alle Dynamics auf einmal laden
        String allJson = com.dronesim.api.ApiClient.getJson("/api/dronedynamics/?limit=" + total + "&offset=0");
        return parser.parseDynamics(allJson);
    }
}

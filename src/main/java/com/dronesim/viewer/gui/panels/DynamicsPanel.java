package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dronesim.controller.DynamicsController;
import com.dronesim.model.DroneDynamics;
import com.dronesim.viewer.gui.components.DroneDynamicsCard;
import com.dronesim.viewer.gui.paging.DronePaginationView;
import com.dronesim.model.DroneDynamicsDataProvider;

/**
 * A panel that displays drone dynamics data using card components.
 * Supports pagination, automatic refresh, and shows a timestamp of the last update.
 */

public class DynamicsPanel extends JPanel implements DronePaginationView<DroneDynamics> {

    private final JPanel cardContainer;
    private final JLabel pageLabel;
    private final JButton prevBtn;
    private final JButton nextBtn;
    private DynamicsController controller;

    private int currentPage = 0;
    private Timer refreshTimer;
    private final int refreshIntervalMs = 5000; // 5 seconds

    private final JLabel lastUpdatedLabel = new JLabel("Last updated: --");

    public DynamicsPanel() {
        setLayout(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField("31", 5);
        JButton loadBtn = new JButton("Load Drone");
        topPanel.add(new JLabel("Dynamics Drone ID:"));
        topPanel.add(idField);
        topPanel.add(loadBtn);
        add(topPanel, BorderLayout.NORTH);

        cardContainer = new JPanel(new GridLayout(0, 2, 10, 10));
        add(new JScrollPane(cardContainer), BorderLayout.CENTER);

        JPanel nav = new JPanel(new BorderLayout());

        JPanel navControls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevBtn = new JButton("<");
        nextBtn = new JButton(">");
        pageLabel = new JLabel();
        navControls.add(prevBtn);
        navControls.add(pageLabel);
        navControls.add(nextBtn);

        nav.add(navControls, BorderLayout.CENTER);
        nav.add(lastUpdatedLabel, BorderLayout.EAST); 

        add(nav, BorderLayout.SOUTH);

        controller = new DynamicsController(null, this); 

        prevBtn.addActionListener(e -> {
            if (currentPage > 0) controller.loadPage(currentPage - 1);
        });
        nextBtn.addActionListener(e -> controller.loadPage(currentPage + 1));

        loadBtn.addActionListener(e -> {
            try {
                int droneId = Integer.parseInt(idField.getText().trim());
                controller.setProvider(new DroneDynamicsDataProvider(droneId));
                controller.loadPage(0);
                startAutoRefresh();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ungültige Drone-ID", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void updatePage(List<DroneDynamics> entries, int currentPage, int pageSize) {
        this.currentPage = currentPage;
        cardContainer.removeAll();
        for (DroneDynamics dyn : entries) {
            cardContainer.add(new DroneDynamicsCard(dyn));
        }

        pageLabel.setText("Page " + (currentPage + 1));
        prevBtn.setEnabled(currentPage > 0);
        nextBtn.setEnabled(entries.size() == pageSize);

        java.time.LocalTime now = java.time.LocalTime.now().withNano(0);
        lastUpdatedLabel.setText("Last updated: " + now);
        System.out.println("[DynamicsPanel] Refreshed at " + now);

        revalidate();
        repaint();
    }

    
    public void startAutoRefresh() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }

        refreshTimer = new Timer(refreshIntervalMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.loadPage(currentPage);
                }
            }
        });
        refreshTimer.start();
    }

    public void stopAutoRefresh() {
        if (refreshTimer != null && refreshTimer.isRunning()) {
            refreshTimer.stop();
        }
    }
}

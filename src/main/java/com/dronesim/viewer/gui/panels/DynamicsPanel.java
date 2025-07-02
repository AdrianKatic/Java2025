package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
// // 
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.dronesim.controller.DynamicsController;
import com.dronesim.model.DroneDynamics;
import com.dronesim.model.PagedDataProvider;
import com.dronesim.viewer.gui.components.DroneDynamicsCard;
import com.dronesim.viewer.gui.paging.DronePaginationView;

public class DynamicsPanel extends JPanel implements DronePaginationView<DroneDynamics> {

    private final JPanel cardContainer;
    private final JLabel pageLabel;
    private final JButton prevBtn;
    private final JButton nextBtn;
    private final DynamicsController controller;

    private int currentPage = 0;
    private List<DroneDynamics> currentData;
    private int pageSize = 10;

    // Timer for automatic page refresh//
    private Timer refreshTimer;
    private final int refreshIntervalMs = 5000; // 5 seconds

    // NEW: Last updated timestamp label //
    private final JLabel lastUpdatedLabel = new JLabel("Last updated: --");

    public DynamicsPanel(PagedDataProvider<DroneDynamics> provider) {
        setLayout(new BorderLayout(5, 5));
        cardContainer = new JPanel(new GridLayout(0, 2, 10, 10));
        add(new JScrollPane(cardContainer), BorderLayout.CENTER);

        // Create nav panel using BorderLayout to support right-aligned timestamp //
        JPanel nav = new JPanel(new BorderLayout());

        // Navigation buttons panel (centered) //
        JPanel navControls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevBtn = new JButton("<");
        nextBtn = new JButton(">");
        pageLabel = new JLabel();
        navControls.add(prevBtn);
        navControls.add(pageLabel);
        navControls.add(nextBtn);

        // Add components to nav panel //
        nav.add(navControls, BorderLayout.CENTER);
        nav.add(lastUpdatedLabel, BorderLayout.EAST); // Right side

        add(nav, BorderLayout.SOUTH);

        controller = new DynamicsController(provider, this);

        prevBtn.addActionListener(e -> {
            if (currentPage > 0) controller.loadPage(currentPage - 1);
        });
        nextBtn.addActionListener(e -> controller.loadPage(currentPage + 1));

        controller.loadPage(0);
        startAutoRefresh();
    }

    @Override
    public void updatePage(List<DroneDynamics> entries, int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.currentData = entries;

        cardContainer.removeAll();
        for (DroneDynamics dyn : entries) {
            cardContainer.add(new DroneDynamicsCard(dyn));
        }

        pageLabel.setText("Page " + (currentPage + 1));
        prevBtn.setEnabled(currentPage > 0);
        nextBtn.setEnabled(entries.size() == pageSize);

        // NEW: Update last updated label and log //
        java.time.LocalTime now = java.time.LocalTime.now().withNano(0);
        lastUpdatedLabel.setText("Last updated: " + now);
        System.out.println("[DynamicsPanel] Refreshed at " + now);

        revalidate();
        repaint();
    }

    // //
    public void startAutoRefresh() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }

        refreshTimer = new Timer(refreshIntervalMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadPage(currentPage);
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

package com.dronesim.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.dronesim.model.DroneDynamics;
import com.dronesim.model.PagedDataProvider;
import com.dronesim.viewer.gui.paging.DronePaginationPanel;

public class DynamicsController {
    private final PagedDataProvider<DroneDynamics> provider;
    private final DronePaginationPanel panel;
    private final int PAGE_SIZE = 10;

    public DynamicsController(PagedDataProvider<DroneDynamics> provider, DronePaginationPanel panel) {
        this.provider = provider;
        this.panel = panel;
    }

    public void loadPage(int page) {
        SwingWorker<List<DroneDynamics>, Void> worker = new SwingWorker<>() {
            private Exception error;

            @Override
            protected List<DroneDynamics> doInBackground() {
                try {
                    return provider.getPage(page, PAGE_SIZE);
                } catch (Exception e) {
                    error = e;
                    return List.of();
                }
            }

            @Override
            protected void done() {
                if (error != null) {
                    JOptionPane.showMessageDialog(null,
                            "Fehler beim Laden: " + error.getMessage(),
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    List<DroneDynamics> data = get();
                    if (data.isEmpty() && page != 0) return;
                    panel.updatePage(data, page, PAGE_SIZE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}

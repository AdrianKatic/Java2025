package com.dronesim.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.dronesim.model.DroneDynamics;
import com.dronesim.model.PagedDataProvider;
import com.dronesim.viewer.gui.paging.DronePaginationView;

public class DynamicsController {
    private PagedDataProvider<DroneDynamics> provider;
    private final DronePaginationView<DroneDynamics> view;
    private final int PAGE_SIZE = 10;

    public DynamicsController(PagedDataProvider<DroneDynamics> provider, DronePaginationView<DroneDynamics> view) {
        this.provider = provider;
        this.view = view;
    }

    public void setProvider(PagedDataProvider<DroneDynamics> provider) {
        this.provider = provider;
    }

    public void loadPage(int page) {
        if (provider == null) {
            JOptionPane.showMessageDialog(null, "No data provider set.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
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
                    view.updatePage(data, page, PAGE_SIZE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}

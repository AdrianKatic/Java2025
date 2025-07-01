package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

    public DynamicsPanel(PagedDataProvider<DroneDynamics> provider) {
        setLayout(new BorderLayout(5, 5));
        cardContainer = new JPanel(new GridLayout(0, 2, 10, 10));
        add(new JScrollPane(cardContainer), BorderLayout.CENTER);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevBtn = new JButton("<");
        nextBtn = new JButton(">");
        pageLabel = new JLabel();

        nav.add(prevBtn);
        nav.add(pageLabel);
        nav.add(nextBtn);
        add(nav, BorderLayout.SOUTH);

        controller = new DynamicsController(provider, this);

        prevBtn.addActionListener(e -> {
            if (currentPage > 0) controller.loadPage(currentPage - 1);
        });
        nextBtn.addActionListener(e -> controller.loadPage(currentPage + 1));

        controller.loadPage(0);
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
        revalidate();
        repaint();
    }
}
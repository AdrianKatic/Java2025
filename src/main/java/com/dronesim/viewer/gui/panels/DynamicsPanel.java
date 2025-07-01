// package com.dronesim.viewer.gui.panels;

// import javax.swing.*;
// import java.awt.*;
// import java.util.List;

// import com.dronesim.model.DroneDynamics;
// import com.dronesim.model.PagedDataProvider;
// import com.dronesim.viewer.gui.components.DroneDynamicsCard;

// public class DynamicsPanel extends JPanel{
//     private final PagedDataProvider<DroneDynamics> provider;
//     private final JPanel cardContainer;
//     private final JButton prevBtn;
//     private final JButton nextBtn;
//     private final JLabel pageLabel;
    
//     private final int PAGE_SIZE = 10;
//     private int currentPage = 0;
//     private List<DroneDynamics> currentPageData;
    
//     public DynamicsPanel(PagedDataProvider<DroneDynamics> provider) {
//         this.provider = provider;
//         setLayout(new BorderLayout(5, 5));
        
//         cardContainer = new JPanel();
//         cardContainer.setLayout(new GridLayout(0, 2, 10, 10));
//         add(new JScrollPane(cardContainer), BorderLayout.CENTER);

//         JPanel nav = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         prevBtn = new JButton("<");
//         nextBtn = new JButton(">");
//         pageLabel = new JLabel();

//         nav.add(prevBtn);
//         nav.add(pageLabel);
//         nav.add(nextBtn);
//         add(nav, BorderLayout.SOUTH);

//         prevBtn.addActionListener(e -> {
//             if (currentPage > 0) {
//                 loadPage(currentPage - 1);
//             }
//         });

//         nextBtn.addActionListener(e -> {
//             loadPage(currentPage + 1);
//         });

//         loadPage(0);
//     }

//     private void loadPage(int page) {
//         if (page < 0) return;
//         prevBtn.setEnabled(false);
//         nextBtn.setEnabled(false);
//         pageLabel.setText("Loading…");

//         new SwingWorker<List<DroneDynamics>, Void>() {
//             private Exception error;

//             @Override
//             protected List<DroneDynamics> doInBackground() {
//                 try {
//                     return provider.getPage(page, PAGE_SIZE);
//                 } catch (Exception ex) {
//                     error = ex;
//                     return List.of();
//                 }
//             }

//             @Override
//             protected void done() {
//                 if (error != null) {
//                     JOptionPane.showMessageDialog(
//                         DynamicsPanel.this,
//                         "Error while loading data\n" + error.getMessage(),
//                         "Loading Error",
//                         JOptionPane.ERROR_MESSAGE
//                     );
//                     return;
//                 }
//                 try {
//                     currentPageData = get();
//                     // If empfy page is not first, stay
//                     if (currentPageData.isEmpty() && page != 0) {
//                         return;
//                     }
//                     currentPage = page;
//                     refreshCards();
//                 } catch (Exception ex) {
//                     ex.printStackTrace();
//                 }
//             }
//         }.execute();
//     }
//     private void refreshCards() {
//         cardContainer.removeAll();
//         for (DroneDynamics dyn : currentPageData) {
//             cardContainer.add(new DroneDynamicsCard(dyn));
//         }
//         pageLabel.setText("Page " + (currentPage + 1));
//         prevBtn.setEnabled(currentPage > 0);
//         nextBtn.setEnabled(currentPageData.size() == PAGE_SIZE);
//         revalidate();
//         repaint();
//     }
// }
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
import com.dronesim.viewer.gui.paging.DronePaginationPanel;

public class DynamicsPanel extends JPanel implements DronePaginationPanel<DroneDynamics> {

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
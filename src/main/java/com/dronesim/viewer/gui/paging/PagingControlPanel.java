// package com.dronesim.viewer.gui.paging;

// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;

// import com.dronesim.model.DroneDynamics;
// import com.dronesim.model.PagedDataProvider;

// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.util.List;


// public class DronePaginationPanel extends JPanel {
//     private JTable table;
//     private DefaultTableModel tableModel;
//     private JButton prevButton, nextButton;
//     private JLabel pageLabel;
//     private int currentPage = 0;
//     private final int PAGE_SIZE = 10;
//     private final PagedDataProvider<DroneDynamics> dataProvider;

//     public DronePaginationPanel(PagedDataProvider<DroneDynamics> dataProvider) {
//         this.dataProvider = dataProvider;
//         setLayout(new BorderLayout());

//         tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Speed", "Status"}, 0);
//         table = new JTable(tableModel);
//         add(new JScrollPane(table), BorderLayout.CENTER);

//         JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         prevButton = new JButton("<");
//         nextButton = new JButton(">");
//         pageLabel = new JLabel();

//         navPanel.add(prevButton);
//         navPanel.add(pageLabel);
//         navPanel.add(nextButton);
//         add(navPanel, BorderLayout.SOUTH);

//         prevButton.addActionListener(this::handlePrev);
//         nextButton.addActionListener(this::handleNext);

//         updateTable();
//         updatePageLabel();
//     }

//     private void handlePrev(ActionEvent e) {
//         if (currentPage > 0) {
//             currentPage--;
//             updateTable();
//             updatePageLabel();
//         }
//     }

//     private void handleNext(ActionEvent e) {
//         currentPage++;
//         updateTable();
//         updatePageLabel();
//     }

//     private void updateTable() {
//         tableModel.setRowCount(0);
//         try {
//             List<DroneDynamics> pageData = dataProvider.getPage(currentPage, PAGE_SIZE);
//             for (DroneDynamics d : pageData) {
//                 tableModel.addRow(new Object[]{
//                     d.getDrone(),
//                     d.getTimestamp(),
//                     d.getSpeed(),
//                     d.getBatteryStatus(),
//                     d.getStatus()
//                 });
//             }
//         } catch (Exception ex) {
//             JOptionPane.showMessageDialog(this,
//             "Error while loading data:\n" + ex.getMessage(),
//             "Loading Error", JOptionPane.ERROR_MESSAGE);
//         }
//         prevButton.setEnabled(currentPage > 0);
//         nextButton.setEnabled(tableModel.getRowCount() == PAGE_SIZE);
//         pageLabel.setText("Page " + (currentPage + 1));
//     }

//     private void updatePageLabel() {
//         pageLabel.setText("Page " + (currentPage + 1));
//     }
// }
package com.dronesim.viewer.gui.paging;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Wiederverwendbares Panel mit "< Page X >"-Steuerung.
 * Delegiert die Seitenwechsel-Logik an die aufrufende Komponente.
 */
public class PagingControlPanel extends JPanel {

    private final JButton prevButton;
    private final JButton nextButton;
    private final JLabel pageLabel;

    private int currentPage = 0;

    public PagingControlPanel(ActionListener onPrev, ActionListener onNext) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        prevButton = new JButton("<");
        nextButton = new JButton(">");
        pageLabel = new JLabel("Page 1");

        prevButton.addActionListener(onPrev);
        nextButton.addActionListener(onNext);

        add(prevButton);
        add(pageLabel);
        add(nextButton);

        updateControls(false); // initial
    }

    public void setCurrentPage(int pageNumber) {
        this.currentPage = pageNumber;
        pageLabel.setText("Page " + (pageNumber + 1));
    }

    public void updateControls(boolean hasNextPage) {
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(hasNextPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void incrementPage() {
        currentPage++;
        setCurrentPage(currentPage);
    }

    public void decrementPage() {
        if (currentPage > 0) {
            currentPage--;
            setCurrentPage(currentPage);
        }
    }
}
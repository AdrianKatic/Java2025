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

import java.util.List;

public interface DronePaginationPanel<T> {
    void updatePage(List<T> entries, int currentPage, int pageSize);
}
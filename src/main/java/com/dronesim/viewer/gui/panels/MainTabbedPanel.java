package com.dronesim.viewer.gui.panels;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.dronesim.model.DroneDynamicsDataProvider;

public class MainTabbedPanel extends JPanel {
    private final JTabbedPane tabs;
    private DynamicsPanel dynamicsPanel;
    
    public MainTabbedPanel() {
        setLayout(new BorderLayout());

        // 1) Steuer-Panel oben: ID-Feld + Load-Button
        JPanel control = new JPanel();
        JTextField idField = new JTextField("31", 5);
        JButton loadBtn = new JButton("Load Drone");
        control.add(new JLabel("Drone ID:"));
        control.add(idField);
        control.add(loadBtn);
        add(control, BorderLayout.NORTH);

        // 2) Tab‐Pane
        tabs = new JTabbedPane();
        tabs.addTab("Dashboard", new DashboardPanel(Integer.parseInt(idField.getText())));
        // Dynamics‐Tab initial
        dynamicsPanel = createDynamicsPanel(Integer.parseInt(idField.getText()));
        tabs.addTab("Dynamics", dynamicsPanel);
        add(tabs, BorderLayout.CENTER);
        tabs.addTab("Drone Catalog", new CatalogPanel());
        
        // 3) Action: bei Klick das Dynamics-Panel neu mit der neuen ID laden
        loadBtn.addActionListener(e -> {
            try {
                int newId = Integer.parseInt(idField.getText().trim());
                // Dashboard neu laden (falls dort ID relevant ist)
                tabs.setComponentAt(0, new DashboardPanel(newId));
                // Dynamics neu laden
                dynamicsPanel = createDynamicsPanel(newId);
                tabs.setComponentAt(1, dynamicsPanel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this, "Bitte eine gültige Zahl eingeben.",
                    "Ungültige ID", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    /** Fabrikmethode für ein frisches DynamicsPanel mit neuem Provider */
    private DynamicsPanel createDynamicsPanel(int droneId) {
        return new DynamicsPanel(
            new DroneDynamicsDataProvider(droneId)
        );
    }
}

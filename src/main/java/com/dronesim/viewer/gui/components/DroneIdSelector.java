package com.dronesim.viewer.gui.components;

import java.awt.FlowLayout;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DroneIdSelector extends JPanel{
   public DroneIdSelector(IntConsumer onLoad) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField("31", 5);
        JButton loadBtn = new JButton("Load Drone");

        add(new JLabel("Dynamics Drone ID:"));
        add(idField);
        add(loadBtn);

        loadBtn.addActionListener(e -> {
            try {
                int droneId = Integer.parseInt(idField.getText().trim());
                onLoad.accept(droneId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ungültige Drone-ID", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
    } 
}

package com.dronesim.viewer.gui.frame;

import javax.swing.JFrame;

import com.dronesim.viewer.gui.panels.MainTabbedPanel;

/**
 * Main app window with all panels inside.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Drone Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setContentPane(new MainTabbedPanel());
    }
}

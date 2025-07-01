package com.dronesim.viewer.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.dronesim.viewer.gui.dialogs.TokenLoginDialog;
import com.dronesim.viewer.gui.frame.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame dummyFrame = new JFrame();
            dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dummyFrame.setLocationRelativeTo(null);

            TokenLoginDialog loginDialog = new TokenLoginDialog(dummyFrame);
            loginDialog.setVisible(true);

            if (!loginDialog.isConfirmed()) {
                System.exit(0);
            }

            MainFrame app = new MainFrame();
            app.setVisible(true);
        });
    }
}

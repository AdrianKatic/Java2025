package com.dronesim;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.dronesim.viewer.gui.dialogs.TokenLoginDialog;
import com.dronesim.viewer.gui.frame.MainFrame;

/**
 * Starts the drone app.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // get Token & URL from user per Dialog
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);

                TokenLoginDialog dialog = new TokenLoginDialog(frame);
                dialog.setVisible(true);


                // Only continue if user confirmed the dialog
                if (!dialog.isConfirmed()) {
                    System.exit(0);
                }
                if (dialog.getToken().isEmpty() || dialog.getUrl().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Token or URL not entered.");
                    System.exit(0);
                }


                // Test connection before opening main window
                com.dronesim.api.ApiConfig config = new com.dronesim.api.ApiConfig();
                com.dronesim.api.ApiClient client = new com.dronesim.api.ApiClient(config);
                if (!client.testConnection()) {
                    JOptionPane.showMessageDialog(null, "Connection failed: Invalid token or URL.");
                    System.exit(0);
                }

                // GUI starts
                MainFrame app = new MainFrame();
                app.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error at start: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}

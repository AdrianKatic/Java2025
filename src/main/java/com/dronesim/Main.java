package com.dronesim;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.dronesim.viewer.gui.dialogs.TokenLoginDialog;
import com.dronesim.viewer.gui.frame.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Token & URL vom User per Dialog holen
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                TokenLoginDialog dialog = new TokenLoginDialog(frame);
                dialog.setVisible(true);

                String token = dialog.getToken();
                String url = dialog.getUrl();

                if (token == null || token.isEmpty() || url == null || url.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Token oder URL nicht eingegeben.");
                    System.exit(0);
                }

                // GUI starten
                MainFrame app = new MainFrame();
                app.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Start: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}

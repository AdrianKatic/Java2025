package com.dronesim.viewer.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.dronesim.api.ApiConfig;

public class TokenLoginDialog extends JDialog {
    private JTextField tokenField;
    private JTextField urlField;
    private JCheckBox saveBox;
    private boolean confirmed = false;
    private static final String CONFIG_FILE = "src/main/resources/config.properties";

    public TokenLoginDialog(JFrame parent) {
        super(parent, "Login - API Zugang", true);
        setLayout(new BorderLayout());
        setSize(400, 220);
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        tokenField = new JTextField(30);
        urlField = new JTextField(30);
        saveBox = new JCheckBox("Token speichern", true);

        loadDefaults();

        inputPanel.add(new JLabel("API Token:"));
        inputPanel.add(tokenField);
        inputPanel.add(new JLabel("API URL:"));
        inputPanel.add(urlField);
        inputPanel.add(new JLabel());
        inputPanel.add(saveBox);

        JButton okBtn = new JButton("Verbinden");
        JButton cancelBtn = new JButton("Abbrechen");
        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        add(inputPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        okBtn.addActionListener(e -> {
            confirmed = true;
            if (saveBox.isSelected()) ApiConfig.overrideAndSave(getToken(),getUrl());
            dispose();
        });

        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
    }

    private void loadDefaults() {
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(in);
            tokenField.setText(props.getProperty("api.token", ""));
            urlField.setText(props.getProperty("api.baseUrl", ""));
        } catch (IOException ignored) {}
    }

    // private void saveProperties() {
    //     Properties props = new Properties();
    //     props.setProperty("api.token", getToken());
    //     props.setProperty("api.baseUrl", getUrl());
    //     try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
    //         props.store(out, "Saved API credentials");
    //     } catch (IOException e) {
    //         JOptionPane.showMessageDialog(this, "Fehler beim Speichern der Konfiguration.", "Fehler", JOptionPane.ERROR_MESSAGE);
    //     }
    // }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getToken() {
        return tokenField.getText().trim();
    }

    public String getUrl() {
        return urlField.getText().trim();
    }
}
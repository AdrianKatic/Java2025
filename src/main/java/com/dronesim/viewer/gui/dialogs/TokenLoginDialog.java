package com.dronesim.viewer.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
        super(parent, "Login - API", true);
        setLayout(new BorderLayout());
        setSize(400, 220);
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        tokenField = new JTextField();
        urlField = new JTextField();
        tokenField.setPreferredSize(new java.awt.Dimension(400, 25));
        urlField.setPreferredSize(new java.awt.Dimension(400, 25));
        saveBox = new JCheckBox("Save Token", true);

        loadDefaults();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        inputPanel.add(new JLabel("API Token:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        inputPanel.add(tokenField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        inputPanel.add(new JLabel("API URL:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        inputPanel.add(urlField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel(), gbc);
        gbc.gridx = 1;
        inputPanel.add(saveBox, gbc);

        JButton okBtn = new JButton("Connect");
        JButton cancelBtn = new JButton("Cancel");
        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        add(inputPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        okBtn.addActionListener(e -> {
            confirmed = true;
            if (saveBox.isSelected()) ApiConfig.overrideAndSave(getUrl(),getToken());
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
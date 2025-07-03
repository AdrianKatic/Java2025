package com.dronesim.viewer.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.dronesim.model.DroneDynamics;

/**
 * A UI panel that displays detailed drone dynamics information in a card format.
 */
public class DroneDynamicsCard extends JPanel{
    private static final DecimalFormat BATTERY_FORMAT = new DecimalFormat("0.00");

    public DroneDynamicsCard(DroneDynamics dyn) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.GRAY, 1, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 130));

        JLabel typeLabel = new JLabel("Type: " + dyn.getTypeName());
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.BOLD));

        String pct = BATTERY_FORMAT.format(dyn.getBatteryStatus());
        JLabel battery = new JLabel("Battery: " + pct + "%");
        JLabel status = new JLabel("Status: " + dyn.getStatus());
        JLabel timestamp = new JLabel("Timestamp: " + dyn.getTimestamp());
        JLabel speed = new JLabel("Speed: " + dyn.getSpeed() + " km/h");
        JLabel gps = new JLabel("GPS: longitude: " + dyn.getLongitude() + " - latitude: " + dyn.getLatitude());
        JLabel seen = new JLabel("Last seen: " + dyn.getLastScene());

        add(typeLabel);
        add(battery);
        add(status);
        add(timestamp);
        add(speed);
        add(gps);
        add(seen);
    }
}

package com.dronesim.gui.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

import com.dronesim.model.DroneDynamicsDataProvider;
import com.dronesim.model.PagedDataProvider;
import com.dronesim.model.DroneDynamics;

public class DroneDynamicsCard extends JPanel{
    public DroneDynamicsCard(DroneDynamics dyn) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.GRAY, 1, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 130));

        JLabel typeLabel = new JLabel("Type: " + dyn.getTypeName());
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.BOLD));

        JLabel battery = new JLabel("Battery: " + dyn.getBatteryStatus() + "%");
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

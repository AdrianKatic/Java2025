package com.dronesim.viewer.gui.pichart;

import java.util.List;

import com.dronesim.model.DroneDynamics;

/**
 * Service to get the status of all drones.
 */
public class DroneStatusService {
    private int onlineCount;
    private int issueCount;
    private int offlineCount;

    /**
     * Constructor for DroneStatusService.
     *
     * @param droneDtos List of drone DTOs to analyze statuses from
     */
    public DroneStatusService(List<DroneDynamics> droneDtos) {
        checkDroneStatuses(droneDtos);
    }

    private void checkDroneStatuses(List<DroneDynamics> droneDtos) {
        for (DroneDynamics droneDto : droneDtos) {
            String status = droneDto.getStatus();
            switch (status) {
                case "ON" -> onlineCount++;
                case "IS" -> issueCount++;
                case "OF" -> offlineCount++;
            }
        }
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public int getOfflineCount() {
        return offlineCount;
    }
}
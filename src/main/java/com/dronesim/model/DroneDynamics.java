package com.dronesim.model;

public class DroneDynamics {

    private String drone;

    private String timestamp;

    private double speed;

    private double alignRoll;
    private double alignPitch;
    private double alignYaw;

    private double latitude;
    private double longitude;

    private double batteryStatus;

    private String lastScene;

    private String status;

    private String typeName;

    public DroneDynamics(String drone, String timestamp, double speed, double alignRoll,
            double alignPitch, double alignYaw, double latitude, double longitude,
            double batteryStatus, String lastScene, String status) {
        this.drone = drone;
        this.timestamp = timestamp;
        this.speed = speed;
        this.alignRoll = alignRoll;
        this.alignPitch = alignPitch;
        this.alignYaw = alignYaw;
        this.latitude = latitude;
        this.longitude = longitude;
        this.batteryStatus = batteryStatus;
        this.lastScene = lastScene;
        this.status = status;
    }

    public DroneDynamics() {
    }


    public String getDrone() {
        return drone;
    }
       public void setDrone(String drone) {
        this.drone = drone;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAlignRoll() {
        return alignRoll;
    }

    public void setAlignRoll(double alignRoll) {
        this.alignRoll = alignRoll;
    }

    public double getAlignPitch() {
        return alignPitch;
    }

    public void setAlignPitch(double alignPitch) {
        this.alignPitch = alignPitch;
    }

    public double getAlignYaw() {
        return alignYaw;
    }

    public void setAlignYaw(double alignYaw) {
        this.alignYaw = alignYaw;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(double batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getLastScene() {
        return lastScene;
    }

    public void setLastScene(String lastScene) {
        this.lastScene = lastScene;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }



    @Override
    public String toString() {
        return "DroneDynamics{"
                + "drone='" + drone + '\''
                + ", timestamp='" + timestamp + '\''
                + ", speed=" + speed + '\''
                + ", alignRoll=" + alignRoll + '\''
                + ", alignPitch=" + alignPitch + '\''
                + ", alignYaw=" + alignYaw + '\''
                + ", latitude=" + latitude + '\''
                + ", longitude=" + longitude + '\''
                + ", batteryStatus=" + batteryStatus + '\''
                + ", lastScene='" + lastScene + '\''
                + ", status='" + status + '\''
                + '}';
    }
}

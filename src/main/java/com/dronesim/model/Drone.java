package com.dronesim.model;

public class Drone {

    private int id;

    private String droneType;

    private String created;

    private String serialNumber;

    private int carriageWeight;

    private String carriageType;

    public Drone(int id, String droneType, String created, String serialNumber, int carriageWeight, String carriageType) {
        this.id = id;
        this.droneType = droneType;
        this.created = created;
        this.serialNumber = serialNumber;
        this.carriageWeight = carriageWeight;
        this.carriageType = carriageType;

    }

    // Default constructor – required for JSON libraries
    public Drone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDroneType() {
        return droneType;
    }

    public void setDroneType(String droneType) {
        this.droneType = droneType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getCarriageWeight() {
        return carriageWeight;
    }

    public void setCarriageWeight(int carriageWeight) {
        this.carriageWeight = carriageWeight;
    }

    public String getCarriageType() {
        return carriageType;
    }

    public void setCarriageType(String carriageType) {
        this.carriageType = carriageType;
    }

    @Override
    public String toString() {
        return "Drone{"
                + "id=" + id
                + ", droneType='" + droneType + '\''
                + ", created='" + created + '\''
                + ", serialNumber='" + serialNumber + '\''
                + ", carriageWeight=" + carriageWeight
                + ", carriageType='" + carriageType + '\''
                + '}';

    }

}

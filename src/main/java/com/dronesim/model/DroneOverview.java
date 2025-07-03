package com.dronesim.model;

public class DroneOverview {
   private final Drone drone;
   private final DroneType type;
   private final DroneDynamics  dynamics;

   public DroneOverview(Drone drone, DroneType type, DroneDynamics dynamics) {
      this.drone = drone;
      this.type = type;
      this.dynamics = dynamics;
   }


   public Drone getDrone() {
      return drone;
   }

   public DroneType getType() {
      return type;
   }

   public DroneDynamics getDynamics() {
      return dynamics;
   }

   public int getId() {
      return drone.getId();
   }

   public String getSerialNumber() {
      return drone.getSerialNumber();
   }

   public double getCarriageWeight() {
      return drone.getCarriageWeight();
   }

   public String getCarriageType() {
      return drone.getCarriageType();
   }

   public String getStatus() {
      return dynamics.getStatus();
   }

   public int getMaxSpeed() {
      return type.getMaxSpeed();
   }

   public String getTypeName() {
      return type.getTypename();
   }
   
   public void setId(int id) {
      this.drone.setId(id);
   }

   public void setSerialNumber(String serialNumber) {
      this.drone.setSerialNumber(serialNumber);
   }

   public void setCarriageWeight(double weight) {
      this.drone.setCarriageWeight((int) weight);
   }

   public void setCarriageType(String type) {
      this.drone.setCarriageType(type);
   }

   public void setStatus(String status) {
      this.dynamics.setStatus(status);
   }

   public void setMaxSpeed(int speed) {
      this.type.setMaxSpeed(speed);
   }

   public void setTypeName(String name) {
      this.type.setTypename(name);
   }
}

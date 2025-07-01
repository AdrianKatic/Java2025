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
      return drone.getCarriage_weight();
   }

   public String getCarriageType() {
      return drone.getCarriage_type();
   }

   public String getStatus() {
      return dynamics.getStatus();
   }

   public int getMaxSpeed() {
      return type.getMax_speed();
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
      this.drone.setCarriage_weight((int) weight);
   }

   public void setCarriageType(String type) {
      this.drone.setCarriage_type(type);
   }

   public void setStatus(String status) {
      this.dynamics.setStatus(status);
   }

   public void setMaxSpeed(int speed) {
      this.type.setMax_speed(speed);
   }

   public void setTypeName(String name) {
      this.type.setTypename(name);
   }
}

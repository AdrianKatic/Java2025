package com.dronesim.api;

import com.dronesim.model.*;
import java.util.List;


public interface DataProvider {
    List<DroneType> parseDroneTypes(String json) throws Exception;
    List<Drone> parseDrones(String json) throws Exception;
    List<DroneDynamics> parseDynamics (String json) throws Exception;
}
package com.dronesim.api;

import java.util.List;

import com.dronesim.model.Drone;
import com.dronesim.model.DroneDynamics;
import com.dronesim.model.DroneType;

/**
 * Parses JSON to drone objects.
 */
public interface DataProvider {

    List<DroneType> parseDroneTypes(String json) throws Exception;

    List<Drone> parseDrones(String json) throws Exception;

    List<DroneDynamics> parseDynamics(String json) throws Exception;
}

package com.dronesim.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dronesim.api.DataProvider;
import com.dronesim.model.Drone;
import com.dronesim.model.DroneDynamics;
import com.dronesim.model.DroneType;


 public class ManualJsonParser implements DataProvider {

    private static String extractResultsArray(String fullJson) {
        int idx = fullJson.indexOf("\"results\":");
        if (idx < 0) {
            throw new RuntimeException("Kein results-Feld gefunden");
        }
        int start = fullJson.indexOf('[', idx);
        int end = fullJson.indexOf(']', start + 1);
        if (start < 0 || end < 0) {
            throw new RuntimeException("Ungültiges JSON-Format");
        }
        return fullJson.substring(start + 1, end).trim();
    }

    @Override
    public List<DroneType> parseDroneTypes(String json) throws Exception {
        String array = extractResultsArray(json);
        if (array.isEmpty()) {
            return List.of();
        }

        List<DroneType> list = new ArrayList<>();
        String[] items = array.split("\\},\\s*\\{");    // split
        for (String item : items) {
            item = item.replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
            Map<String, String> map = toMap(item);   // map key values
            DroneType dt = new DroneType();
            dt.setId(Integer.parseInt(map.get("id")));
            dt.setManufacturer(map.get("manufacturer"));
            dt.setTypename(map.get("typename"));
            dt.setWeight(Integer.parseInt(map.get("weight")));
            dt.setMaxSpeed(Integer.parseInt(map.get("max_speed")));
            dt.setBatteryCapacity(Integer.parseInt(map.get("battery_capacity")));
            dt.setControlRange(Integer.parseInt(map.get("control_range")));
            dt.setMaxCarriage(Integer.parseInt(map.get("max_carriage")));
            list.add(dt);
        }
        return list;
    }

    // parse drone objects from json file
    @Override
    public List<Drone> parseDrones(String json) throws Exception {
        String array = extractResultsArray(json);
        if (array.isEmpty()) {
            return List.of();
        }

        List<Drone> list = new ArrayList<>();
        String[] items = array.split("\\},\\s*\\{");
        for (String item : items) {
            item = item.replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
            Map<String, String> map = toMap(item);
            Drone d = new Drone();
            d.setId(Integer.parseInt(map.get("id")));
            d.setDroneType(map.get("dronetype"));
            d.setCreated(map.get("created"));
            d.setSerialNumber(map.get("serialnumber"));
            d.setCarriageWeight(Integer.parseInt(map.get("carriage_weight")));
            d.setCarriageType(map.get("carriage_type"));
            list.add(d);
        }
        return list;
    }

    // parse drone dynamics from json file
    @Override
    public List<DroneDynamics> parseDynamics(String json) throws Exception {
        String array = extractResultsArray(json);
        if (array.isEmpty()) {
            return List.of();
        }

        List<DroneDynamics> list = new ArrayList<>();
        String[] items = array.split("\\},\\s*\\{");
        for (String item : items) {
            item = item.replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
            Map<String, String> map = toMap(item);
            DroneDynamics dd = new DroneDynamics();
            dd.setDrone(map.get("drone"));
            dd.setTimestamp(map.get("timestamp"));
            dd.setSpeed(Double.parseDouble(map.get("speed")));
            dd.setAlignRoll(Double.parseDouble(map.getOrDefault("align_roll", "0")));
            dd.setAlignPitch(Double.parseDouble(map.getOrDefault("align_pitch", "0")));
            dd.setAlignYaw(Double.parseDouble(map.getOrDefault("align_yaw", "0")));

            dd.setLatitude(Double.parseDouble(map.getOrDefault("latitude", "0")));
            dd.setLongitude(Double.parseDouble(map.getOrDefault("longitude", "0")));
            dd.setBatteryStatus((Double.parseDouble(map.getOrDefault("battery_status", "0"))));
            dd.setLastScene(map.getOrDefault("last_seen", ""));
            dd.setStatus(map.getOrDefault("status", "offline"));
            list.add(dd);
        }
        return list;
    }

    private static Map<String, String> toMap(String item) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = item.split(",\\s*");
        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            String key = kv[0].trim().replaceAll("^\"|\"$", "");
            String value = kv[1].trim().replaceAll("^\"|\"$", "");
            map.put(key, value);
        }
        return map;
    }

    public List<String> extractLabels(String json) {
        String array = extractResultsArray(json);
        if (array.isEmpty()) {
            return List.of();
        }
        /*
         * @return first object form json
         */
         
        String first = array.split("\\},\\s*\\{")[0]
                .replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
        Map<String, String> map = toMap(first);
        return new ArrayList<>(map.keySet());
    }

    public Map<String, String> extractFirstObject(String json) {
        String array = extractResultsArray(json);
        if (array.isEmpty()) {
            return Map.of();
        }
    
        String firstRaw = array.split("\\},\\s*\\{")[0]
                .replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
        return toMap(firstRaw);
    }


    public Integer findDroneIdByType(String fullJson, int typeId) throws Exception {
        String array = extractResultsArray(fullJson);
        if (array.isEmpty()) {
            return null;
        }
        String[] items = array.split("\\},\\s*\\{");
        for (String item : items) {
            String clean = item.replaceAll("^[\\{\\s]+|[\\}\\s]+$", "");
            Map<String, String> map = toMap(clean);
            int droneId = Integer.parseInt(map.get("id"));
            String typeUrl = map.get("dronetype"); 
            int parsedTypeId = Integer.parseInt(
                typeUrl.replaceAll(".*/(\\d+)/?$", "$1")
            );
            if (parsedTypeId == typeId) {
                return droneId;
            }
        }
        return null;
    }

}
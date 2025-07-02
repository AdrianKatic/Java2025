package com.dronesim.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dronesim.model.Drone;
import com.dronesim.model.DroneDynamics;
import com.dronesim.model.DroneOverview;
import com.dronesim.model.DroneType;
import com.dronesim.parser.ManualJsonParser;


public class DataFetcher {
    private final ApiClient client;
    private final ManualJsonParser parser;
    private static final Map<Integer, DroneType> typeCache = new HashMap<>();

    public DataFetcher() {
        ApiConfig cfg = new ApiConfig();
        this.client = new ApiClient(cfg);
        this.parser = new ManualJsonParser();
    }


    /*
     * @param limit, number of entries to fetch
     * @param offset, offset for pagination
     * @param List of parsed DroneDynamics objects
    */

    
    public void fetchDroneDynamicsWithPagination() throws Exception, InterruptedException {
        String path = "/api/dronedynamics/?limit=20&offset=0";  // Start-URL

        while (path != null) {
            String jsonResponse = client.getJson(path);
            List<DroneDynamics> droneDynamics = new ManualJsonParser().parseDynamics(jsonResponse);
            droneDynamics.forEach(System.out::println);
            path = extractNextPageUrl(jsonResponse);

        }
    }
    

    private String extractNextPageUrl(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            if (json.has("next") && json.getString("next") != null) {
                return json.getString("next");
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void fetchDroneDynamicsWithPaginationConfirmation(int limit, int droneId) throws Exception {
        Scanner scanner = new Scanner(System.in);
        try{
            String path = "/api/" + droneId + "/dynamics/?limit=" + limit + "&offset=0";

            while (path != null) {
                // Debug
                System.out.println("DEBUG " + path);

                String json = client.getJson(path);
                
                // List<DroneDynamics> page = parser.parseDynamics(json);
                parser.parseDynamics(json).forEach(System.out::println);
                
                // page.forEach(System.out::println);

                String nextRaw = new JSONObject(json).optString("next", null);

                if (nextRaw == null || nextRaw.isEmpty()) {
                    path = null;
                } else {
                    System.out.print("Load next page? (y/n) ");
                    String answer = scanner.nextLine().trim();
                    if (!answer.equalsIgnoreCase("y")) {
                        break;
                    }
                    URI nextUri = URI.create(nextRaw);
                    String rel = nextUri.getPath() + (nextUri.getQuery() != null ? "?" + nextUri.getQuery():"");
                    System.out.println("Using relative path: " + rel);
                    path = rel;
                }
            }    
        } finally {
            scanner.close();
        }
    }


    public List<DroneDynamics> fetchDroneDynamics(int droneId, int limit, int offset) throws Exception {
        String path = "/api/" + droneId + "/dynamics/?limit=" + limit + "&offset=" + offset;
        String json = client.getJson(path);
        List<DroneDynamics> list = parser.parseDynamics(json);
    
        if (typeCache.isEmpty()) {
            String typesJson = client.getJson("/api/dronetypes/?limit=100&offset=0");
            List<DroneType> types = parser.parseDroneTypes(typesJson);
            for (DroneType t : types) {
                typeCache.put(t.getId(), t);
            }
        }
        
        for (DroneDynamics dd : list) {
            String url = dd.getDrone();
            URI uri = URI.create(url);
            Pattern p = Pattern.compile(".*/(\\d+)/?$");
            Matcher m = p.matcher(uri.getPath());
            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                DroneType t = typeCache.get(id);
                if (t != null) {
                    dd.setTypeName(t.getTypename());
                    double pct;
                    double raw = dd.getBatteryStatus();    // roher API-Wert
                    int maxCap = t.getBattery_capacity();  // z.B. 5000 mAh
                    if (maxCap < 1000) {
                        pct = (raw / maxCap) * 10 ;
                        dd.setBatteryStatus(pct);
                        System.out.println("pct:" + pct);
                    } else {
                        pct = (raw * 100) / maxCap;
                        dd.setBatteryStatus(pct);
                        System.out.println("pct:" + pct);
                    }
                } else {
                    dd.setTypeName("Unknown");
                } 
            } else {
                dd.setTypeName("Unknown");
            }

        }

        return list;
    }

    public List<DroneDynamics> fetchDroneDynamicsForDrone(int droneId, int limit, int offset) throws Exception {
        return fetchDroneDynamics(droneId, limit, offset);
    }

    public List<DroneType> fetchAllDroneTypes() throws Exception {
        // Erstes Request, um Gesamtzahl abzurufen
        String firstJson = client.getJson("/api/dronetypes/?limit=1&offset=0");
        JSONObject firstRoot = new JSONObject(firstJson);
        int total = firstRoot.getInt("count");
        // Dann alle auf einmal laden
        String json = client.getJson("/api/dronetypes/?limit=" + total + "&offset=0");
        return parser.parseDroneTypes(json);
    }


    public List<DroneDynamics> fetchAllDroneDynamics(int limit, int offset) throws Exception {
        String path = "/api/dronedynamics/?limit=" + limit + "&offset=" + offset;
        String json = client.getJson(path);
        List<DroneDynamics> list = parser.parseDynamics(json);

        if (typeCache.isEmpty()) {
            String typesJson = client.getJson("/api/dronetypes/?limit=100&offset=0");
            List<DroneType> types = parser.parseDroneTypes(typesJson);
            for (DroneType t : types) {
                typeCache.put(t.getId(), t);
            }
        }

        for (DroneDynamics dd : list) {
            String url = dd.getDrone();
            URI uri = URI.create(url);
            Pattern p = Pattern.compile(".*/(\\d+)/?$");
            Matcher m = p.matcher(uri.getPath());
            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                DroneType t = typeCache.get(id);
                if (t != null) {
                    // (1) Typnamen setzen
                    dd.setTypeName(t.getTypename());
                    // (2) echten Ladezustand in Prozent berechnen
                    double raw = dd.getBatteryStatus();    // roher API-Wert
                    System.out.println("raw: " + raw);
                    int maxCap = t.getBattery_capacity();  // z.B. 5000 mAh
                    System.out.println("maxCap: " + maxCap);
                    double percent = raw / maxCap * 100.0;
                    System.out.println("percent: " + percent);
                    dd.setBatteryStatus(percent);
                } else {
                    dd.setTypeName("Unknown");
                } 
            } else {
                dd.setTypeName("Unknown");
            }

        }

        return list;
    }

    
    public List<Drone> fetchAllDrones() throws Exception {
        // Erstes Request, um Gesamtzahl abzurufen
        String firstJson = client.getJson("/api/drones/?limit=1&offset=0");
        JSONObject firstRoot = new JSONObject(firstJson);
        int total = firstRoot.getInt("count");

        // Dann alle auf einmal laden
        String json = client.getJson("/api/drones/?limit=" + total + "&offset=0");
        return parser.parseDrones(json);
    }

    public List<DroneOverview> fetchAllDroneOverviews() throws Exception {
        List<Drone> drones = fetchAllDrones();
        List<DroneDynamics> dynamics = fetchAllDroneDynamics(200, 0);
        List<DroneType> types = fetchAllDroneTypes();

        Map<Integer, Drone> droneMap = new HashMap<>();
        for (Drone d : drones) {
            droneMap.put(d.getId(), d);
        }

        Map<Integer, DroneType> typeMap = new HashMap<>();
        for (DroneType t : types) {
            typeMap.put(t.getId(), t);
        }

        List<DroneOverview> result = new ArrayList<>();
        for (DroneDynamics dd : dynamics) {
            String url = dd.getDrone();
            Matcher m = Pattern.compile(".*/(\\d+)/?$").matcher(URI.create(url).getPath());
            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                Drone d = droneMap.get(id);
                if (d != null) {
                    DroneType type = typeMap.get(d.getDronetype());
                    DroneOverview overview = new DroneOverview(d,type,dd);
                    overview.setId(d.getId());
                    overview.setSerialNumber(d.getSerialNumber());
                    overview.setCarriageWeight(d.getCarriage_weight());
                    overview.setCarriageType(d.getCarriage_type());
                    overview.setStatus(dd.getStatus());
                    if (type != null) {
                        overview.setMaxSpeed(type.getMax_speed());
                        overview.setTypeName(type.getTypename());
                    }
                    result.add(overview);
                }
            }
        }

        return result;
    }
}
package com.dronesim.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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





    public void fetchDroneDynamicsWithPaginationConfirmation(int limit, int droneId) throws Exception {
        Scanner scanner = new Scanner(System.in);
        try{
            String path = "/api/" + droneId + "/dynamics/?limit=" + limit + "&offset=0";

            while (path != null) {
                System.out.println("DEBUG " + path);

                String json = client.getJson(path);
                
                parser.parseDynamics(json).forEach(System.out::println);

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
        String dynJson = client.getJson(
            "/api/" + droneId + "/dynamics/?limit=" + limit + "&offset=" + offset
        );
        List<DroneDynamics> list = parser.parseDynamics(dynJson);
        ensureTypeCache();
        String singleDroneJson = client.getJson("/api/drones/" + droneId + "/");
        String typeUrl = new JSONObject(singleDroneJson).getString("dronetype");
        int typeId = Integer.parseInt(typeUrl.replaceAll(".*/(\\d+)/?$", "$1"));
        DroneType dt = typeCache.get(typeId);
        for (DroneDynamics dd : list) {
            if (dt != null) {
                dd.setTypeName(dt.getTypename());
                double raw = dd.getBatteryStatus();
                double cap = dt.getBatteryCapacity();
                double pct = (cap > 0) ? raw * 100.0 / cap : 0;
                dd.setBatteryStatus(Math.max(0, Math.min(100, pct)));
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
        return fetchAllItems("/api/dronetypes/", parser::parseDroneTypes);
    }


    public List<DroneDynamics> fetchAllDroneDynamics(int limit, int offset) throws Exception {
        String path = "/api/dronedynamics/?limit=" + limit + "&offset=" + offset;
        String json = client.getJson(path);
        List<DroneDynamics> list = parser.parseDynamics(json);
        ensureTypeCache();
        for (DroneDynamics dd : list) {
            String url = dd.getDrone();
            URI uri = URI.create(url);
            Pattern p = Pattern.compile(".*/(\\d+)/?$", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(uri.getPath());
            if (m.find()) {
                int id = Integer.parseInt(m.group(1));
                DroneType t = typeCache.get(id);
                if (t != null) {
                    dd.setTypeName(t.getTypename());
                    double raw = dd.getBatteryStatus();
                    int maxCap = t.getBatteryCapacity();
                    double percent = (maxCap > 0) ? raw / maxCap * 100.0 : 0;
                    dd.setBatteryStatus(Math.max(0, Math.min(100, percent)));
                } else {
                    dd.setTypeName("Unknown");
                }
            } else {
                dd.setTypeName("Unknown");
            }
        }
        return list;
    }

    private void ensureTypeCache() throws Exception {
        if (typeCache.isEmpty()) {
            List<DroneType> types = fetchAllDroneTypes();
            for (DroneType t : types) {
                typeCache.put(t.getId(), t);
            }
        }
    }

    
    public List<Drone> fetchAllDrones() throws Exception {
        return fetchAllItems("/api/drones/", parser::parseDrones);
    }

    private <T> List<T> fetchAllItems(String basePath, ParserFunction<String, List<T>> parserFunc) throws Exception {
        String firstJson = client.getJson(basePath + "?limit=1&offset=0");
        JSONObject firstRoot = new JSONObject(firstJson);
        int total = firstRoot.getInt("count");
        String json = client.getJson(basePath + "?limit=" + total + "&offset=0");
        return parserFunc.apply(json);
    }

    @FunctionalInterface
    private interface ParserFunction<T, R> {
        R apply(T t) throws Exception;
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
                    DroneType type = typeMap.get(d.getDroneType());
                    DroneOverview overview = new DroneOverview(d,type,dd);
                    overview.setId(d.getId());
                    overview.setSerialNumber(d.getSerialNumber());
                    overview.setCarriageWeight(d.getCarriageWeight());
                    overview.setCarriageType(d.getCarriageType());
                    overview.setStatus(dd.getStatus());
                    if (type != null) {
                        overview.setMaxSpeed(type.getMaxSpeed());
                        overview.setTypeName(type.getTypename());
                    }
                    result.add(overview);
                }
            }
        }

        return result;
    }

    public int[] fetchAllDroneStatusCounts() throws Exception {
        List<Drone> drones = fetchAllDrones();

        int online  = 0;
        int offline = 0;
        int issue   = 0;

        for (Drone d : drones) {
            List<DroneDynamics> dyns = fetchDroneDynamicsForDrone(d.getId(), 1, 0);
            if (dyns.isEmpty()) continue;
            String status = dyns.get(0).getStatus();
            switch (status) {
                case "ON" -> online++;
                case "OF" -> offline++;
                case "IS" -> issue++;
            }
        }

        return new int[]{ online, offline, issue };
    }

}
package com.dronesim.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ApiConfig {

    private final String baseUrl;
    private final String token;

    // Default: load from config.properties
    public ApiConfig() {
        Properties props = new Properties();
        Path configPath = Path.of("config.properties");
        try (InputStream in = Files.newInputStream(configPath)) {
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("config.properties not found or error while loading configuration");
        }
        this.baseUrl = props.getProperty("api.baseUrl");
        this.token = props.getProperty("api.token");
        if (baseUrl == null || token == null) {
            throw new RuntimeException("api.baseUrl or api.token missing in config.properties");
        }
    }

    // New: construct directly
    public ApiConfig(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }


    public static void overrideAndSave(String baseUrl, String token) {
        Properties props = new Properties();
        Path configPath = Path.of("config.properties");

        try (InputStream in = Files.newInputStream(configPath)) {
            props.load(in);
        } catch (IOException e) {
            // Datei existiert nicht, ignoriere
        }

        props.setProperty("api.baseUrl", baseUrl);
        props.setProperty("api.token", token);

        try (OutputStream out = Files.newOutputStream(configPath)) {
            props.store(out, "Updated API configuration");
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Konfiguration: " + e.getMessage());
        }
    }


    public String getBaseUrl() { return baseUrl; }
    public String getToken() { return token; }
}

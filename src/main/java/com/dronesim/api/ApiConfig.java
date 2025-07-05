package com.dronesim.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Stores base URL and token used for API requests.
 */
public class ApiConfig {

    private final String baseUrl;
    private final String token;

    /**
     * Loads API config from file.
     */
    public ApiConfig() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new RuntimeException("config.properties not found");
            }
            Properties props = new Properties();
            props.load(in);

            this.baseUrl = props.getProperty("api.baseUrl");
            this.token = props.getProperty("api.token");

            if (baseUrl == null || token == null) {
                throw new RuntimeException("api.baseUrl or api.token missing in config.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration");
        }
    }

    /**
     * Saves new config values to file and updates instance fields.
     */
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

    /**
     * @return base API URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @return authentication token
     */
    public String getToken() {
        return token;
    }
}

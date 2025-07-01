package com.dronesim.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private final HttpClient client;
    private final String baseUrl;
    private final String token;

    public ApiClient(ApiConfig cfg) {
        
        // build client that follows redirects
        this.client = HttpClient.newBuilder()
            .followRedirects(Redirect.ALWAYS)
            .build();
        this.baseUrl = cfg.getBaseUrl();    // base api url
        this.token = cfg.getToken();    // token from config
    }


    // send get-request to path and returns json as string
    public String getJson(String path) throws IOException, InterruptedException {

        // http request
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + path))
            .header("Authorization", "Token " + token)
            .header("Accept", "application/json")
            .GET()
            .build();

        // send request + get response body
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        int status = resp.statusCode();
        if (status < 200 || status >= 300) {
            throw new RuntimeException("GET " + path + " -> HTTP " + status);
        }
        return resp.body();
    }

    public boolean testConnection() {
        try {
            String response = getJson("/api/");
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}

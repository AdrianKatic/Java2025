package com.dronesim.test;

import com.dronesim.api.ApiClient;
import com.dronesim.api.ApiConfig;
import com.dronesim.api.DataFetcher;

public class ApiExample {
    public static void main (String[] args) {     
        try {
            ApiConfig cfg = new ApiConfig();
            ApiClient client = new ApiClient(cfg);

            DataFetcher dataFetcher2 = new DataFetcher();
            System.out.println(dataFetcher2.fetchAllDroneTypes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
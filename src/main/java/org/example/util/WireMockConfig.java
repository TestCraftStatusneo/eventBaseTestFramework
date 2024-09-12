package org.example.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WireMockConfig {
    private static final int PORT = 8089;

    private static final String WIREMOCK_URL = "http://localhost:" + PORT + "/api/data";

    private static WireMockServer wireMockServer;

    public static void start() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(PORT));
        wireMockServer.start();
        System.out.println("Wiremock started");
    }

    public static void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
            System.out.println("Wiremock stopped");
        }
    }

    public static String fetchDataFromApi() {
        StringBuilder response = new StringBuilder();
        try {
            URL apiUrl = new URL(WIREMOCK_URL);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
package com.ginger.aitest.core.services.weather;

public class WeatherService {

    private String ipAddress;
    private String port;

    public WeatherService(String ip, String port) {
        this.ipAddress = ip;
        this.port = port;
    }

    public String call() {
        return "IP: " + ipAddress + " PORT: " + port;
    }

    private String buildQueryString() {
        return "";
    }
}

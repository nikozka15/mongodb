package com.nikozka.app.models;

public class ProjectProperties {
    private final String username;

    private final String password;
    private final String clusterUrl;

    public ProjectProperties(String username, String password, String clusterUrl) {
        this.username = username;
        this.password = password;
        this.clusterUrl = clusterUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClusterUrl() {
        return clusterUrl;
    }
}

package com.nikozka.app.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientProvider {
    private MongoClientProvider() {}
    public static MongoClient createMongoClient(String username, String password, String clusterUrl) {
        String connectionString = String.format("mongodb+srv://%s:%s@%s/?retryWrites=true&w=majority", username, password, clusterUrl);

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        return MongoClients.create(settings);
    }
}

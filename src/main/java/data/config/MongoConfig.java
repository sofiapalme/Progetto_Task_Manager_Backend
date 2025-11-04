package data.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MongoConfig {

    @ConfigProperty(name = "mongodb.uri")
    String uri;

    @ConfigProperty(name = "mongodb.database")
    String databaseName;

    private MongoClient client;

    public MongoClient getClient() {
        if (client == null) {
            client = MongoClients.create(uri);
        }
        return client;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}

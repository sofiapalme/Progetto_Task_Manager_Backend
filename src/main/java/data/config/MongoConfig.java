package data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MongoConfig {

    private static MongoClient mongoClient;

    static {
        try (InputStream input = MongoConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String uri = prop.getProperty("mongodb.uri");
            mongoClient = MongoClients.create(uri);

        } catch (IOException ex) {
            throw new RuntimeException("Impossibile leggere application.properties", ex);
        }
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }
}

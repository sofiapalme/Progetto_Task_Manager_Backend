package data.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import data.config.MongoConfig;
import data.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class UserRepository {

    @Inject
    MongoConfig config;

    public User authenticate(String email, String password) {
        User user = findByEmail(email);
        if (user == null) {
            return null;
        }

        boolean matches = BCrypt.checkpw(password, user.getPassword());
        if (matches) {
            return user;
        } else {
            return null;
        }
    }

    /** Ottiene la collection users */
    private MongoCollection<Document> getUserCollection() {
        return config.getClient()
                .getDatabase(config.getDatabaseName())
                .getCollection("users");
    }

    // ADD: inserisce e criptaggio password
    public void add(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        Document doc = new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append("password", hashed);

        getUserCollection().insertOne(doc);

        // assegno l'id generato al POJO
        user.setId(doc.getObjectId("_id"));
    }

    // FIND BY EMAIL
    public User findByEmail(String email) {
        Document doc = getUserCollection()
                .find(Filters.eq("email", email))
                .first();

        if (doc == null) return null;

        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setUsername(doc.getString("username"));
        user.setEmail(doc.getString("email"));
        user.setPassword(doc.getString("password"));
        return user;
    }

    // UPDATE PASSWORD
    public boolean updatePassword(String id, String newPassword) {
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        var result = getUserCollection().updateOne(
                Filters.eq("_id", new ObjectId(id)),
                new Document("$set", new Document("password", hashed))
        );

        return result.getModifiedCount() > 0;
    }

    // UPDATE EMAIL
    public boolean updateEmail(String id, String newEmail) {
        var result = getUserCollection().updateOne(
                Filters.eq("_id", new ObjectId(id)),
                new Document("$set", new Document("email", newEmail))
        );

        return result.getModifiedCount() > 0;
    }

    // UPDATE USERNAME
    public boolean updateUsername(String id, String newUsername) {
        var result = getUserCollection().updateOne(
                Filters.eq("_id", new ObjectId(id)),
                new Document("$set", new Document("username", newUsername))
        );

        return result.getModifiedCount() > 0;
    }
}

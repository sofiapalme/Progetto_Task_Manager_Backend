package data.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@MongoEntity(collection = "users")
public class User {
    private ObjectId id;
    private String username;
    private String email;
    private String password;
}
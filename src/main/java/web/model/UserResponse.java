package web.model;

import org.bson.types.ObjectId;

public class UserResponse {
    private ObjectId id;
    private String email;

    public UserResponse(ObjectId id, String email) {
        this.id = id;
        this.email = email;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


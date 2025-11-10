package data.model;

import org.bson.types.ObjectId;

public class Collaborator {

    private ObjectId idUser;
    private String status;

    public Collaborator() {
    }

    public Collaborator(ObjectId idUser, String status) {
        this.idUser = idUser;
        this.status = status;
    }

    public ObjectId getIdUser() {
        return idUser;
    }

    public void setIdUser(ObjectId idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

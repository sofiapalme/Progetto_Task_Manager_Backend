package data.model;

import org.bson.types.ObjectId;

public class Collaborator {

    private ObjectId idUser;
    private Role role;

    public Collaborator() {
    }

    public Collaborator(ObjectId idUser, Role role) {
        this.idUser = idUser;
        this.role = role;
    }

    public ObjectId getIdUser() {
        return idUser;
    }

    public void setIdUser(ObjectId idUser) {
        this.idUser = idUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

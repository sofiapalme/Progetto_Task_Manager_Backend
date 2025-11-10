package data.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import java.util.List;

@MongoEntity(collection = "projects")
public class Project extends PanacheMongoEntity {

    private ObjectId id;
    private String titolo;
    private String avanzamento;
    private List<String> fasi;
    private List<Collaborator> collaboratori;

    public Project() {
    }

    public Project(ObjectId id, String titolo, String avanzamento, List<String> fasi, List<Collaborator> collaboratori) {
        this.id = id;
        this.titolo = titolo;
        this.avanzamento = avanzamento;
        this.fasi = fasi;
        this.collaboratori = collaboratori;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(String avanzamento) {
        this.avanzamento = avanzamento;
    }

    public List<String> getFasi() {
        return fasi;
    }

    public void setFasi(List<String> fasi) {
        this.fasi = fasi;
    }

    public List<Collaborator> getCollaboratori() {
        return collaboratori;
    }

    public void setCollaboratori(List<Collaborator> collaboratori) {
        this.collaboratori = collaboratori;
    }
}

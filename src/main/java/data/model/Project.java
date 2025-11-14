package data.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import java.util.List;

@MongoEntity(collection = "projects")
public class Project extends PanacheMongoEntity {

    private ObjectId id;
    private String titolo;
    private String descrizione;
    private List<String> fasi;
    private boolean completato;
    private List<User> team;
    private User creatore;

    public Project() {
    }

    public Project(ObjectId id, String titolo, String descrizione, List<String> fasi, boolean completato, List<User> team, User creatore) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.fasi = fasi;
        this.completato = completato;
        this.team = team;
        this.creatore = creatore;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<String> getFasi() {
        return fasi;
    }

    public void setFasi(List<String> fasi) {
        this.fasi = fasi;
    }

    public boolean isCompletato() {
        return completato;
    }

    public void setCompletato(boolean completato) {
        this.completato = completato;
    }

    public List<User> getTeam() {
        return team;
    }

    public void setTeam(List<User> team) {
        this.team = team;
    }

    public User getCreatore() { return creatore; }

    public void setCreatore(User creatore) { this.creatore = creatore; }
}

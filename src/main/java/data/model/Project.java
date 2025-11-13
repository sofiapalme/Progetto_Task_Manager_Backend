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
    private String avanzamento;
    private List<String> fasi;
    private boolean completato;
    private List<Collaborator> team;

    public Project() {
    }

    public Project(ObjectId id, String titolo, String descrizione, String avanzamento, List<String> fasi, boolean completato, List<Collaborator> team) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.avanzamento = avanzamento;
        this.fasi = fasi;
        this.completato = completato;
        this.team = team;
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

    public boolean isCompletato() {
        return completato;
    }

    public void setCompletato(boolean completato) {
        this.completato = completato;
    }

    public List<Collaborator> getTeam() {
        return team;
    }

    public void setTeam(List<Collaborator> team) {
        this.team = team;
    }
}

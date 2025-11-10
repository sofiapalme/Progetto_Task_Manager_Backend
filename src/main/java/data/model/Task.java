package data.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

@MongoEntity(collection = "tasks")
public class Task extends PanacheMongoEntity {

    private ObjectId id;
    private String titolo;
    private String descrizione;
    private String fase;
    private List<String> etichette;
    private List<String> assegnatari;
    private Date dataScadenza;
    private ObjectId idProgetto;

    public Task() {
    }

    public Task(ObjectId id, String titolo, String descrizione, String fase, List<String> etichette,
                List<String> assegnatari, Date dataScadenza, ObjectId idProgetto) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.fase = fase;
        this.etichette = etichette;
        this.assegnatari = assegnatari;
        this.dataScadenza = dataScadenza;
        this.idProgetto = idProgetto;
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

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public List<String> getEtichette() {
        return etichette;
    }

    public void setEtichette(List<String> etichette) {
        this.etichette = etichette;
    }

    public List<String> getAssegnatari() {
        return assegnatari;
    }

    public void setAssegnatari(List<String> assegnatari) {
        this.assegnatari = assegnatari;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public ObjectId getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(ObjectId idProgetto) {
        this.idProgetto = idProgetto;
    }
}

package data.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@MongoEntity(collection = "tasks")
public class Task {
    private ObjectId id;
    private String titolo;
    private String descrizione;
    private String fase;
    private List<String> etichette;
    private List<String> assegnatari;
    private Date dataScadenza;
    private ObjectId idProgetto;
}

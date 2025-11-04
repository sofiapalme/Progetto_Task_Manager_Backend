package data.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@MongoEntity(collection = "projects")
public class Project {
    private ObjectId id;
    private String titolo;
    private String avanzamento;
    private List<String> fasi;
    private List<Collaboratore> collaboratori ;
}

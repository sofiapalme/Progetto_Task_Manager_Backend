package data.repository;

import data.model.Project;
import data.model.Task;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ProjectRepository implements PanacheMongoRepository<Project> {

    public List<Project> getAllProjectsByUser(ObjectId id) {
        return find("collaboratori.idUser in ?1", List.of(id)).list();
    }


}

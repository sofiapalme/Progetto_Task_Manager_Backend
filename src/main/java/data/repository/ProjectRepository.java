package data.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import data.config.MongoConfig;
import data.model.Collaborator;
import data.model.Project;
import data.model.Role;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectRepository implements PanacheMongoRepository<Project> {

    @Inject
    MongoConfig config;

    public MongoCollection<Document> getProjectCollection() {
        return config.getClient()
                .getDatabase(config.getDatabaseName())
                .getCollection("project");
    }

    public void createProject(Project project) {
        Document doc = new Document("titolo", project.getTitolo())
                .append("descrizione", project.getDescrizione())
                .append("fasi", project.getFasi())
                .append("completato", project.isCompletato())
                .append("team", project.getTeam());

        getProjectCollection().insertOne(doc);
        project.setId(doc.getObjectId("_id"));
    }

    public List<Project> getAllProjectsByUser(ObjectId id) {
        return find("team.idUser in ?1", List.of(id)).list();
    }

    public void updateProject(ObjectId projectId,Project project) {
        Document doc = new Document("titolo", project.getTitolo())
                .append("descrizione", project.getDescrizione())
                .append("fasi", project.getFasi())
                .append("completato", project.isCompletato())
                .append("team", project.getTeam());

        getProjectCollection().replaceOne(
                Filters.eq("_id", projectId),
                doc
        );
    }

    public boolean deleteProject(ObjectId projectId) {
        var result = getProjectCollection().deleteOne(Filters.eq("_id", projectId));
        return result.getDeletedCount() > 0;
    }

    private Project docToProject(Document doc) {
        if (doc == null) return null;

        Project project = new Project();
        project.setId(doc.getObjectId("_id"));
        project.setTitolo(doc.getString("titolo"));
        project.setDescrizione(doc.getString("descrizione"));
        project.setFasi((List<String>) doc.get("fasi"));
        project.setCompletato(doc.getBoolean("completato"));
        
        // Converti team da List<Document> a List<Collaborator>
        List<Document> teamDocs = (List<Document>) doc.get("team");
        if (teamDocs != null) {
            List<Collaborator> team = teamDocs.stream()
                .map(d -> new Collaborator(
                    d.getObjectId("idUser"),
                    Role.valueOf(d.getString("role"))
                ))
                .collect(Collectors.toList());
            project.setTeam(team);
        } else {
            project.setTeam(new ArrayList<>());
        }
        
        return project;
    }
}

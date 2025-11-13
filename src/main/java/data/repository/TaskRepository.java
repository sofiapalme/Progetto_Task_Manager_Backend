package data.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import data.config.MongoConfig;
import data.model.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TaskRepository {

    @Inject
    MongoConfig config;

    /** Ottiene la collection tasks */
    public MongoCollection<Document> getTaskCollection() {
        return config.getClient()
                .getDatabase(config.getDatabaseName())
                .getCollection("tasks");
    }

    // ADD: inserisce un task
    public void add(Task task) {
        Document doc = new Document("titolo", task.getTitolo())
                .append("descrizione", task.getDescrizione())
                .append("fase", task.getFase())
                .append("etichette", task.getEtichette())
                .append("assegnatari", task.getAssegnatari())
                .append("data_scadenza", task.getDataScadenza())
                .append("idProgetto", task.getIdProgetto());

        getTaskCollection().insertOne(doc);

        task.id = doc.getObjectId("_id");;
    }

    public List<Task> getAll(){
        List<Task> tasks = new ArrayList<>();
        return tasks;
    }

    public Task findById(ObjectId id) {
        Document doc = getTaskCollection().find(Filters.eq("_id", id)).first();
        return docToTask(doc);
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find().forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    public List<Task> findByFase(String fase) {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find(Filters.eq("fase", fase))
                .forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    public List<Task> findByEtichetta(String etichetta) {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find(Filters.eq("etichette", etichetta))
                .forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    public List<Task> findByAssegnatario(String assegnatario) {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find(Filters.eq("assegnatari", assegnatario))
                .forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    public List<Task> findByScadenza(Date scadenza) {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find(Filters.gte("data_scadenza", scadenza))
                .forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    public List<Task> findByProgetto(ObjectId idProgetto) {
        List<Task> tasks = new ArrayList<>();
        getTaskCollection().find(Filters.eq("idProgetto", idProgetto))
                .forEach(doc -> tasks.add(docToTask(doc)));
        return tasks;
    }

    /** Converte un Document in Task */
    private Task docToTask(Document doc) {
        if (doc == null) return null;

        Task task = new Task();
        task.id = doc.getObjectId("_id");                // id pubblico di Panache
        task.setTitolo(doc.getString("titolo"));
        task.setDescrizione(doc.getString("descrizione"));
        task.setFase(doc.getString("fase"));
        task.setEtichette((List<String>) doc.get("etichette"));
        task.setAssegnatari((List<String>) doc.get("assegnatari"));
        task.setDataScadenza(doc.getDate("data_scadenza"));
        task.setIdProgetto(doc.getObjectId("idProgetto"));
        return task;
    }
}

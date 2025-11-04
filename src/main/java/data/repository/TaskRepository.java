package data.repository;

import data.model.Task;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class TaskRepository implements PanacheMongoRepository<Task> {
    public Task findById(ObjectId id) {
        return find("_id", id).firstResult();
    }

    public List<Task> findByFase(String fase) {
        return find("fase", fase).list();
    }

    public List<Task> findByEtichetta(String etichetta) {
        return find("etichette", etichetta).list();
    }

    public List<Task> findByAssegnatario(String assegnatario) {
        return find("assegnatari", assegnatario).list();
    }

    public List<Task> findByScadenza(String scadenza) {
        return find("data_scadenza", scadenza).list();
    }

    public List<Task> findByProgetto(ObjectId idProgetto) {
        return find("idProgetto", idProgetto).list();
    }
}

package service;

import data.model.Task;
import data.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // CREA TASK
    public Task createTask(Task task) {
        taskRepository.add(task);  // usa il metodo add del repository
        return task;
    }

    // TROVA TUTTI I TASK
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // o creare un metodo findAll nel repo
    }

    // TROVA TASK PER FASE
    public List<Task> getTasksByFase(String fase) {
        return taskRepository.findByFase(fase);
    }

    // TROVA TASK PER ETICHETTA
    public List<Task> getTasksByEtichetta(String etichetta) {
        return taskRepository.findByEtichetta(etichetta);
    }

    // TROVA TASK PER ASSEGNATARIO
    public List<Task> getTasksByAssegnatario(String assegnatario) {
        return taskRepository.findByAssegnatario(assegnatario);
    }

    // TROVA TASK PER SCADENZA
    public List<Task> getTasksByScadenza(Date scadenza) {
        return taskRepository.findByScadenza(scadenza);
    }

    // TROVA TASK PER PROGETTO
    public List<Task> getTasksByProgetto(ObjectId progetto) {
        return taskRepository.findByProgetto(progetto);
    }

    // ELIMINA TASK
    public boolean deleteTask(ObjectId id) {
        return taskRepository.getTaskCollection().deleteOne(new org.bson.Document("_id", id)).getDeletedCount() > 0;
    }

    public boolean updateTask (Task task) {
        return taskRepository.update(task);
    }
}

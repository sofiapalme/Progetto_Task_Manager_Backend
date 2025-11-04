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

    public Task createTask(Task task) {
        taskRepository.persistOrUpdate(task);
            return task;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll().list();
    }

    public List<Task> getTasksByFase (String fase) {
        return taskRepository.findByFase(fase);
    }

    public List<Task> getTasksByEtichetta (String etichetta) {
        return taskRepository.findByEtichetta(etichetta);
    }

    public List<Task> getTasksByAssegnatario (String assegnatario) {
        return taskRepository.findByAssegnatario(assegnatario);
    }

    public List<Task> getTasksByScadenza (Date scadenza) {
        return taskRepository.findByScadenza(scadenza);
    }

    public List<Task> getTasksByProgetto (ObjectId progetto) {
        return taskRepository.findByProgetto(progetto);
    }

    public void deleteTask(ObjectId id) {
        taskRepository.deleteById(id);
    }
}
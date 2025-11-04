package data.resource;

import data.model.Task;
import data.repository.TaskRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Path("/task")
public class TaskResource {
    private final TaskRepository taskRepository;

    public TaskResource(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task createTask(Task task) {
        taskRepository.persistOrUpdate(task);
        return task;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTasks() {
        return taskRepository.findAll().list();
    }

    @GET
    @Path("/fase/{fase}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByFase (@PathParam("fase") String fase) {
        return taskRepository.findByFase(fase);
    }

    @GET
    @Path("/etichetta/{etichetta}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByEtichetta (@PathParam("etichetta") String etichetta) {
        return taskRepository.findByEtichetta(etichetta);
    }

    @GET
    @Path("/assegnatario/{assegnatario}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByAssegnatario (@PathParam("assegnatario") String assegnatario) {
        return taskRepository.findByAssegnatario(assegnatario);
    }

    @GET
    @Path("/scadenza/{scadenza}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByScadenza (@PathParam("scadenza") Date scadenza) {
        return taskRepository.findByScadenza(scadenza);
    }

    @GET
    @Path("/progetto/{progetto}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByProgetto (@PathParam("progetto") String progettoId) {
        ObjectId progetto = new ObjectId(progettoId);
        return taskRepository.findByProgetto(progetto);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id") String id) {
        ObjectId key = new ObjectId(id);
        taskRepository.deleteById(key);
    }
}

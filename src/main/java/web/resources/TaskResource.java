package web.resources;

import data.model.Task;
import data.repository.TaskRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import service.TaskService;
import java.util.Date;
import java.util.List;

@Path("/task")
public class TaskResource {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public TaskResource(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
        taskService.createTask(task);
        return Response.ok().build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GET
    @Path("/fase/{fase}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByFase (@PathParam("fase") String fase) {
        return taskService.getTasksByFase(fase);
    }

    @GET
    @Path("/etichetta/{etichetta}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByEtichetta (@PathParam("etichetta") String etichetta) {
        return taskService.getTasksByEtichetta(etichetta);
    }

    @GET
    @Path("/assegnatario/{assegnatario}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByAssegnatario (@PathParam("assegnatario") String assegnatario) {
        return taskService.getTasksByAssegnatario(assegnatario);
    }

    @GET
    @Path("/scadenza/{scadenza}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByScadenza (@PathParam("scadenza") Date scadenza) {
        return taskService.getTasksByScadenza(scadenza);
    }

    @GET
    @Path("/progetto/{progetto}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasksByProgetto (@PathParam("progetto") String progettoId) {
        ObjectId progetto = new ObjectId(progettoId);
        return taskService.getTasksByProgetto(progetto);
    }

    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id") String id) {
        ObjectId key = new ObjectId(id);
        taskService.deleteTask(key);
    }
}

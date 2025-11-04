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
    public Response getAllTasks() {
        taskService.getAllTasks();
        return Response.ok().build();
    }

    @GET
    @Path("/fase/{fase}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByFase (@PathParam("fase") String fase) {
        taskService.getTasksByFase(fase);
        return Response.ok().build();
    }

    @GET
    @Path("/etichetta/{etichetta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByEtichetta (@PathParam("etichetta") String etichetta) {
        taskService.getTasksByEtichetta(etichetta);
        return Response.ok().build();
    }

    @GET
    @Path("/assegnatario/{assegnatario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByAssegnatario (@PathParam("assegnatario") String assegnatario) {
        taskService.getTasksByAssegnatario(assegnatario);
        return Response.ok().build();
    }

    @GET
    @Path("/scadenza/{scadenza}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByScadenza (@PathParam("scadenza") Date scadenza) {
        taskService.getTasksByScadenza(scadenza);
        return Response.ok().build();
    }

    @GET
    @Path("/progetto/{progetto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByProgetto (@PathParam("progetto") String progettoId) {
        ObjectId progetto = new ObjectId(progettoId);
        taskService.getTasksByProgetto(progetto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id") String id) {
        ObjectId key = new ObjectId(id);
        taskService.deleteTask(key);
    }
}

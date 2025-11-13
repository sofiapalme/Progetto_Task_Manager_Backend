package web.resources;
import data.model.Task;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import service.TaskService;
import java.util.Date;
import java.util.List;

@DenyAll
@Path("/task")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PermitAll
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
        taskService.createTask(task);
        return Response.ok().build();
    }

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
        List<Task> taskList = taskService.getAllTasks();
        return Response.ok(taskList).build();
    }

    @PermitAll
    @GET
    @Path("/fase/{fase}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByFase (@PathParam("fase") String fase) {
        List<Task> taskFaseList = taskService.getTasksByFase(fase);
        return Response.ok(taskFaseList).build();
    }

    @PermitAll
    @GET
    @Path("/etichetta/{etichetta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByEtichetta (@PathParam("etichetta") String etichetta) {
        List<Task> taskEtichettaList = taskService.getTasksByEtichetta(etichetta);
        return Response.ok(taskEtichettaList).build();
    }

    @PermitAll
    @GET
    @Path("/assegnatario/{assegnatario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByAssegnatario (@PathParam("assegnatario") String assegnatario) {
        List<Task> taskAssegnatarioList = taskService.getTasksByAssegnatario(assegnatario);
        return Response.ok(taskAssegnatarioList).build();
    }

    @PermitAll
    @GET
    @Path("/scadenza/{scadenza}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByScadenza (@PathParam("scadenza") Date scadenza) {
        List<Task> taskScadenzaList = taskService.getTasksByScadenza(scadenza);
        return Response.ok(taskScadenzaList).build();
    }

    @PermitAll
    @GET
    @Path("/progetto/{progetto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByProgetto (@PathParam("progetto") String progettoId) {
        ObjectId progetto = new ObjectId(progettoId);
        List<Task> taskProgettoList = taskService.getTasksByProgetto(progetto);
        return Response.ok(taskProgettoList).build();
    }

    @PermitAll
    @DELETE
    @Path("/{id}")
    public void deleteTask(@PathParam("id") String id) {
        ObjectId key = new ObjectId(id);
        taskService.deleteTask(key);
    }
}
